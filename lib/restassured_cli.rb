require "restassured_cli/filenames"
require "restassured_cli/userinterface"
require "restassured_cli/createcontroller"

module RestAssuredCLI

  def self.process(firstArg, secondArg, options)
    # checking required first argument for null
    if firstArg.nil?
      puts "Error: Base package name cannot be null\n\n"
      Display.help
      return
    end
    # checking required second argument for null
    if secondArg.nil?
      puts "Error: Service name cannot be null\n\n"
      Display.help
      return
    end

    # setting default values
    if options.nil?
      options[:jdkVersion] = "1.7"
      options[:projectVersion] = "1.0.0"
      options[:full] = false
    end
    if options[:jdkVersion].nil?
      options[:jdkVersion] = "1.7"
    end
    if options[:projectVersion].nil?
      options[:projectVersion] = "1.0.0"
    end
    if options[:full].nil?
      options[:full] = false
    end

    $baseJavaPackage = firstArg

    # servicename = test module name
    $serviceName = secondArg

    # projectName = project root and can contain dashes
    $projectName = $serviceName

    # groupId = java test package names cannot contain dashes
    baseJavaPackageDashesRemoved = $baseJavaPackage.gsub(/-/, '')
    serviceNameDashesRemoved = $serviceName.gsub(/-/, '')
    $groupId = baseJavaPackageDashesRemoved + "." + serviceNameDashesRemoved

    # packageName = test directory path replaces . with /
    $packageName = baseJavaPackageDashesRemoved.gsub(/\./,'/') + '/' + serviceNameDashesRemoved

    filegen = CreateController.new("#{$projectName}", "#{$groupId}", "#{$serviceName}", "#{$packageName}")
    filegen.setJdkVersion(options[:jdkVersion])
    filegen.setProjectVersion(options[:projectVersion])
    files = FileNames.new("#{$serviceName}", "#{$packageName}")
    baseCopyToDir = "#{$projectName}/testing"

    # Create common directories
    filegen.mkdir_common
    filegen.mkdir_service_default
    if options[:full]
      filegen.mkdir_service_full
      files.full.each do |filename|
        filegen.copyFilesWithoutOverwriting "#{filename[0]}", "#{baseCopyToDir}/#{filename[1]}"
      end
    end
    # Default option
    files.default.each do |filename|
      filegen.copyFilesWithoutOverwriting "#{filename[0]}", "#{baseCopyToDir}/#{filename[1]}"
    end
    files.testRunner.each do |filename|
      filegen.copyFilesWithoutOverwriting "#{filename[0]}", "#{baseCopyToDir}/#{filename[1]}"
    end
    Display.results
  end
end
