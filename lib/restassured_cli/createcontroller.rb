require "fileutils"

# Class creates directories and copy files for restassured cli tool.
class CreateController

  def initialize(projectName, groupId, serviceName, packageName)
    # required parameters
    @projectName = projectName
    @groupId = groupId
    @serviceName = serviceName
    @packageName = packageName
    # optional parameters
    @version = '1.0.0-SNAPSHOT'
    @jdkVersion = '1.7'
  end

  def mkdir_common
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-test-runner"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-test-parent-pom"
  end

  # create directories for basic option
  def mkdir_service_default
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-service-tests/src/main/java/"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-service-tests/src/main/resources/"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-service-tests/src/main/java/#{@packageName}/tests/service"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-service-tests/src/main/java/#{@packageName}/tests/serviceintegration"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-service-tests/src/main/resources/default"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-service-tests/src/main/resources/qa"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-service-tests/src/main/resources/ci"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-service-tests/src/main/resources/e2e"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-test-library/src/main/java/"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library/base"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-perf-tests/src/main/java/"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-perf-tests/src/main/resources/"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-perf-tests/src/main/scripts/"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-production-tests/src/main/java/"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-production-tests/src/assembly/"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-production-tests/src/main/resources/production"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-production-tests/src/main/java/#{@packageName}/tests/production"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-perf-tests/src/main/java/#{@packageName}/tests/perf"
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library/sample/base"
  end

  # create tests directories for full option
  def mkdir_service_full
    FileUtils.mkdir_p "#{@projectName}/testing/#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library/schema"
  end


  def setJdkVersion(version)
    @jdkVersion = version
  end

  def setProjectVersion(version)
    @version = version
  end

  # copy poms, testng, tests files and replace with groupId, projectName and serviceName
  def replaceStringsInFile(filename)
    text = File.read(filename)
    text = text.gsub(/GROUPID/, @groupId)
    text = text.gsub(/PROJECTNAME/, @projectName)
    text = text.gsub(/SERVICENAME/, @serviceName)
    text = text.gsub(/PACKAGENAME/, @serviceName)
    text = text.gsub(/PROJECTVERSION/, @version)
    text = text.gsub(/JDKVERSION/, @jdkVersion)
    File.open(filename, "w") { |file| file << text }
  end

  # copy a file only when it does not already exist
  def copyFilesWithoutOverwriting(filenameFrom, filenameTo)
    unless File.file?(filenameTo)
      fullPath = __dir__ + '/source/maven/testing/' + filenameFrom
      FileUtils.cp fullPath, filenameTo
      replaceStringsInFile filenameTo
    end
  end

end
