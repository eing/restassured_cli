# coding: utf-8
lib = File.expand_path('../lib', __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.include?(lib)
require 'restassured_cli/version'

Gem::Specification.new do |spec|
  spec.name          = "restassured_cli"
  spec.version       = RestAssuredCLI::VERSION
  spec.authors       = ["Eing Ong"]
  spec.email         = ["EingOng@gmail.com"]

  spec.summary       = %q{Command Line Utility that generates test projects using RestAssured}
  spec.description   = %q{Command line utility (CLI) to generate test projects and sample tests for web services using RESTAssured.}
  spec.homepage      = "http://rubygems.org/gems/restassured_cli"
  spec.license       = "MIT"

  # Prevent pushing this gem to RubyGems.org by setting 'allowed_push_host', or
  # delete this section to allow pushing this gem to any host.
  if spec.respond_to?(:metadata)
    spec.metadata['allowed_push_host'] = "https://rubygems.org"
  else
    raise "RubyGems 2.0 or newer is required to protect against public gem pushes."
  end

  spec.files         = `git ls-files`.split($/)
  spec.bindir        = "bin"
  spec.executables   = ['restassured_cli']
  spec.require_paths = ["lib"]

  spec.add_development_dependency "bundler"
  spec.add_development_dependency "rake"
  spec.add_development_dependency "rspec"
end
