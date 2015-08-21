# Class to maintain filenames to copy
class FileNames

  attr_reader :default
  attr_reader :full
  attr_reader :testRunner

  def initialize(serviceName, packageName)
    @serviceName = serviceName
    @packageName = packageName
    initializeDefault
    initializeFull
    initializeTestRunner
  end

  def initializeDefault
    @default = [
        ["../pom.xml", "../pom.xml"],
        ["README.md", "README.md"],
        ["pom.xml", "pom.xml"],
        ["sampleservice-perf-tests/pom.xml", "#{@serviceName}-perf-tests/pom.xml"],
        ["sampleservice-perf-tests/testng-perf-notests.xml", "#{@serviceName}-perf-tests/src/main/resources/testng-perf.xml"],
        ["sampleservice-production-tests/pom.xml", "#{@serviceName}-production-tests/pom.xml"],
        ["sampleservice-production-tests/logback.xml", "#{@serviceName}-production-tests/src/main/resources/logback.xml"],
        ["sampleservice-production-tests/testng-production.xml", "#{@serviceName}-production-tests/src/main/resources/testng-production.xml"],
        ["sampleservice-test-library/pom.xml", "#{@serviceName}-test-library/pom.xml"],
        ["sampleservice-test-library/default/ServiceTestBase.java", "#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library/base/ServiceTestBase.java"],
        ["sampleservice-test-library/default/BaseConstant.java", "#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library/base/BaseConstant.java"],
        ["sampleservice-test-library/default/InternalConfigManager.java", "#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library/base/InternalConfigManager.java"],
        ["sampleservice-test-library/default/sample/base/Constant.java", "#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library/sample/base/Constant.java"],
        ["sampleservice-test-library/default/sample/base/SampleTestBase.java", "#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library/sample/base/SampleTestBase.java"],
        ["sampleservice-service-tests/pom.xml", "#{@serviceName}-service-tests/pom.xml"],
        ["sampleservice-service-tests/logback.xml", "#{@serviceName}-service-tests/src/main/resources/logback.xml"],
        ["sampleservice-service-tests/resources/default/sample.properties", "#{@serviceName}-service-tests/src/main/resources/default/sample.properties"],
        ["sampleservice-service-tests/resources/ci/sample.properties", "#{@serviceName}-service-tests/src/main/resources/ci/sample.properties"],
        ["sampleservice-service-tests/resources/qa/sample.properties", "#{@serviceName}-service-tests/src/main/resources/qa/sample.properties"],
        ["sampleservice-service-tests/resources/e2e/sample.properties", "#{@serviceName}-service-tests/src/main/resources/e2e/sample.properties"],
        ["sampleservice-service-tests/default/SampleTest.java", "#{@serviceName}-service-tests/src/main/java/#{@packageName}/tests/service/SampleTest.java"],
        ["sampleservice-service-tests/default/testng-ci.xml", "#{@serviceName}-service-tests/src/main/resources/testng-ci.xml"],
        ["sampleservice-service-tests/default/testng-qa.xml", "#{@serviceName}-service-tests/src/main/resources/testng-qa.xml"],
        ["sampleservice-service-tests/default/testng-e2e.xml", "#{@serviceName}-service-tests/src/main/resources/testng-e2e.xml"]
    ]
  end

  def initializeFull
    @full = [
        ["sampleservice-test-library/full/sample/base/Constant.java", "#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library/sample/base/Constant.java"],
        ["sampleservice-test-library/full/sample/base/SampleTestBase.java", "#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library/sample/base/SampleTestBase.java"],
        ["sampleservice-test-library/full/SampleServiceHelper.java", "#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library/sample/SampleServiceHelper.java"],
        ["sampleservice-test-library/full/Customer.java", "#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library/schema/Customer.java"],
        ["sampleservice-test-library/full/Phone.java", "#{@serviceName}-test-library/src/main/java/#{@packageName}/tests/library/schema/Phone.java"],
        ["sampleservice-service-tests/full/SampleComponentTest.java", "#{@serviceName}-service-tests/src/main/java/#{@packageName}/tests/service/SampleComponentTest.java"],
        ["sampleservice-service-tests/full/SampleServiceIntegrationTest.java", "#{@serviceName}-service-tests/src/main/java/#{@packageName}/tests/serviceintegration/SampleServiceIntegrationTest.java"],
        ["sampleservice-service-tests/full/testng-qa.xml", "#{@serviceName}-service-tests/src/main/resources/testng-qa.xml"],
        ["sampleservice-service-tests/full/testng-ci.xml", "#{@serviceName}-service-tests/src/main/resources/testng-ci.xml"],
        ["sampleservice-service-tests/full/testng-e2e.xml", "#{@serviceName}-service-tests/src/main/resources/testng-e2e.xml"],
        ["sampleservice-perf-tests/LoginTest.java", "#{@serviceName}-perf-tests/src/main/java/#{@packageName}/tests/perf/LoginTest.java"],
        ["sampleservice-perf-tests/testng-perf.xml", "#{@serviceName}-perf-tests/src/main/resources/testng-perf.xml"]
    ]
  end

  def initializeTestRunner
    @testRunner = [
        ["sampleservice-test-runner/pom.xml", "#{@serviceName}-test-runner/pom.xml"],
        ["sampleservice-test-parent-pom/pom.xml", "#{@serviceName}-test-parent-pom/pom.xml"]
    ]
  end

end
