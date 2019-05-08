$script = <<-SCRIPT
echo -e "LANG=\"en_US.UTF-8\"\nLC_CTYPE=\"en_US.UTF-8\"\nLC_ALL=\"en_US.UTF-8\"" > /etc/default/locale
SCRIPT

Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/xenial64"
  config.vm.synced_folder "/Users/sandjaier/testws", "/home/vagrant/testws/"
  config.vm.network "private_network", type: "dhcp"
  config.vm.provision "shell",
    inline: $script
  config.vm.provider "virtualbox" do |vb|
    vb.name = "jenkinsci"
    vb.customize ["modifyvm", :id, "--memory", "2048"]
  end
end
