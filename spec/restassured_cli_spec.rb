require 'spec_helper'

def negativeTestData
  [
    ["nil",       "nil", {}, "Error: Base package name cannot be null"],
    ["com.intuit", nil,  {}, "Error: Service name cannot be null"]
  ]
end

def pkgSvcNamesTestData
  [
      ["com.intuit", "smallbiz", {}, "Created project for smallbiz service"]
  ]
end

def pkgSvcNamesWithOptionsTestData
  [
      ["com.intuit", "smallbusiness", {:jdkVersion => "1.8", :projectVersion => "2.0"}, "Created project for smallbusiness service"]
  ]
end

describe RestAssuredCLI do

  subject  { RestAssuredCLI }

  # Clean up - remove all directories that are successfully created
  after(:context) do
    begin
      Dir.chdir(__dir__ + "/../")
      FileUtils.rm_rf(pkgSvcNamesTestData[0][1])
      FileUtils.rm_rf(pkgSvcNamesWithOptionsTestData[0][1])
    rescue Exception => e
      puts "Directory cannot be deleted"
    end
  end

  describe '#process' do
    context "with negative input arguments" do
      negativeTestData.each do | data |
          # test data setup
          let(:input) { data[0..2] }
          let(:expectedOutput) { data[3] }

          it "should pass negative tests" do
            # test execution
            result = capture(:stdout) { subject.process(input[0], input[1], input[2]) }
            # test verification
            expect(result).to include(expectedOutput)
          end
      end
    end

    context "with package and service name" do
      pkgSvcNamesTestData.each do | data |
        # test data setup
        let(:input) { data[0..2] }
        let(:expectedOutput) { data[3] }

        it "should pass arguments with package, service name tests" do
          # test execution
          result = capture(:stdout) { subject.process(input[0], input[1], input[2]) }
          # test verification
          expect(result).to include(expectedOutput)
        end
      end
    end

    context "with package, service name and options" do
      pkgSvcNamesWithOptionsTestData.each do | data |
        # test data setup
        let(:input) { data[0..2] }
        let(:expectedOutput) { data[3] }

        it "should pass arguments with package, service name and options tests" do
          # test execution
          result = capture(:stdout) { subject.process(input[0], input[1], input[2]) }
          # test verification
          expect(result).to include(expectedOutput)
        end
      end
    end
  end

end