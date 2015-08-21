# restassured_cli
![Gem Version](https://badge.fury.io/rb/restassured_cli.png)

## About

Command line utility (CLI) to generate test projects and sample tests using [RESTAssured BDD framework](https://github.com/jayway/rest-assured), a Java DSL for easy testing of REST services. Also, released as a ruby gem at [rubygems.org](https://rubygems.org/gems/restassured_cli)

## Release notes

* v1.0.0 - First release with examples as presented in [StarWest 2015](http://starwest.techwell.com/sessions/starwest-2015/automate-rest-services-testing-restassured)

## Getting started

### To install as gem
Add this line to your application's Gemfile:

```ruby
gem 'restassured_cli'
```

And then execute:

    $ bundle

Or install it yourself as:

    $ gem install restassured_cli

### To run as ruby executable
* Just run ./bin/restassured_cli

## Usage

```
$ ./restassured_cli
Usage: restassured_cli [-f] [-p projectVersion] [-j jdkVersion] <base package> <service name>
Options
  -f,--full           : full option, generates sample tests
  -j,--jdk            : project JDK version (default: 1.7)
  -p,--projectVersion : project version (default: 1.0.0-SNAPSHOT)
  -h,--help           : display this screen

Examples
   restassured_cli com.intuit.samples smallbusiness
   restassured_cli -p 1.0.0 -f com.intuit.samples smallbusiness

$./restassured_cli -p 1.0.0 -f com.intuit.samples smallbusiness
Created project for smallbusiness service ...

smallbusiness
          |── smallbusiness-service
          |         |── src/main/java/
          |         └── src/test/java/
          └── testing
                    |── smallbusiness-perf-tests
                    |     └── jmeter/regression/
                    |     |── src/main/java/com/intuit/samples/smallbusiness/tests/perf/
                    |     └── src/main/resources/
                    |                   └── testng-perf.xml
                    |── smallbusiness-production-tests
                    |     |── src/main/java/com/intuit/samples/smallbusiness/tests/production/
                    |     └── src/main/resources/
                    |                   └── testng-production.xml
                    |── smallbusiness-service-tests
                    |     |── src/main/java/com/intuit/samples/smallbusiness/tests/service/
                    |     |── src/main/java/com/intuit/samples/smallbusiness/tests/serviceintegration/
                    |     └── src/main/resources/
                    |                   |── default/*.properties
                    |                   |── ci/*.properties
                    |                   |── qa/*.properties
                    |                   |── e2e/*.properties
                    |                   |── testng-ci.xml
                    |                   |── testng-qa.xml
                    |                   └── testng-e2e.xml
                    └── smallbusiness-test-library
                          └── src/main/java/com/intuit/samples/smallbusiness/tests/library/
```

## Maven

* pom files
 - **Root level pom** or project top level pom, should be replaced with your own project pom.
 - testing/project-parent-pom/pom.xml, should contain all your project dependencies
 - testing/project-test-runner/pom.xml, runs tests without recompiling and is used in Jenkins

## How to run tests

* Running test artifacts
 - Tests are run *without* recompiling. Artifacts are downloaded and testng files are extracted.

```
$ cd testing
$ mvn clean install

$ cd testing/<project>-test-runner
$ mvn clean install -U -P ci
$ mvn clean install -U -P qa
$ mvn clean install -U -P e2e
$ mvn clean install -U -P perf
```
 - For profiles other than ci, qa, e2e, perf and production, create your own profiles in test-parent-pom.

## Eclipse

* To import testing project into Eclipse :

 - cd testing; mvn eclipse:eclipse

 - File > Import > Maven > Existing Maven Projects > Browse to testing root directory (all selections checked) and click Finish

* To run tests for environments other than qa, add **-Dtargetenv=ci** in Run Configuration’s VM arguments/options.

## Windows users

* Steps to run CLI
 -  Install Ruby from http://rubyinstaller.org/downloads/
 -  In the directory where you install restassured_cli, run
```
c:\Ruby22-x64\bin\ruby.exe restassured_cli -p 1.0.0 -j 1.7 -f com.intuit.samples smallbusiness
```

## Tips

* Updating project-test-library requires a "mvn install" before tests can pick up the change.
* Remove sample test code in service-tests and test-library when you're done
* TestNG xml files requires updating once you created tests.

## Contributing

Bug reports and pull requests are welcome on GitHub at https://github.com/eing/restassured_cli.

## Contributors (thank you!)

This release contains contributions from the following developers (in alphabetical order) across Intuit. Many others also contributed in different ways.
* Baechul Kim
* Todd Ekenstam

## License

The gem is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).
