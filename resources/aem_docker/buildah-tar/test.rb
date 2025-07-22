describe package('telnetd') do
  it { should_not be_installed }
end

describe inetd_conf do
  its("telnet") { should eq nil }
end

describe file('/root/entrypoint.sh') do
  its('content') { should match(%r{buildah}) }
end

describe command('uname') do
  it { should exist }
  its('stdout') { should eq "Linux\n" }
end

describe command('/bin/sh').exist? do
  it { should eq true }
end

describe command('/root/google-cloud-sdk/bin/gcloud auth activate-service-account --key-file=/root/key.json;/root/google-cloud-sdk/bin/gcloud info') do
  it { should exist }
  its('stdout') { should match(%r{terraform@newdemo-246311.iam.gserviceaccount.com}) }
end

