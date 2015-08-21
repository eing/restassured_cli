# Functions to interact with end user
module Display

  # Command line parsing without external library and minimal code
  def Display.help
    puts "Usage: restassured_cli [-f] [-p projectVersion] [-j jdkVersion] <base package> <service name>"
    puts "Options"
    puts "  -f,--full           : full option, generates sample tests"
    puts "  -j,--jdk            : JDK version (default: 1.7)"
    puts "  -p,--projectVersion : project version (default: 1.0.0-SNAPSHOT)"
    puts "  -h,--help           : display this screen"
    puts "Examples"
    puts "   restassured_cli com.intuit.samples smallbusiness"
    puts "   restassured_cli -p 1.0.0 -j 1.8 -f com.intuit.samples smallbusiness"
  end

  # Display results
  def Display.results
    puts "Created project for #{$serviceName} service ..."
    puts "#{$projectName}"
    puts "          |-- #{$serviceName}-service"
    puts "          |         |-- src/main/java/"
    puts "          |         |-- src/test/java/"
    puts "          |-- testing"
    puts "                    |-- #{$serviceName}-perf-tests"
    puts "                    |     |-- pom.xml"
    puts "                    |     |-- jmeter/regression/"
    puts "                    |     |-- src/main/java/#{$packageName}/tests/perf/"
    puts "                    |     |-- src/main/resources/"
    puts "                    |                    testng-perf.xml"
    puts "                    |-- #{$serviceName}-production-tests"
    puts "                    |     |-- pom.xml"
    puts "                    |     |-- src/main/java/#{$packageName}/tests/production/"
    puts "                    |     |--src/main/resources/"
    puts "                    |                   |-- production/"
    puts "                    |                   |--testng-production.xml"
    puts "                    |-- #{$serviceName}-service-tests"
    puts "                    |     |-- pom.xml"
    puts "                    |     |-- src/main/java/#{$packageName}/tests/service/"
    puts "                    |     |-- src/main/java/#{$packageName}/tests/serviceintegration/"
    puts "                    |     |--src/main/resources/"
    puts "                    |                   |-- ci/"
    puts "                    |                   |-- qa/"
    puts "                    |                   |-- e2e/"
    puts "                    |                   |-- testng-ci.xml"
    puts "                    |                   |-- testng-qa.xml"
    puts "                    |                   |--testng-e2e.xml"
    puts "                    |-- #{$serviceName}-test-library"
    puts "                    |     |-- pom.xml"
    puts "                    |     |--src/main/java/#{$packageName}/tests/library/"
    puts "                    |--pom.xml"
  end

end

