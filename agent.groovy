/*
* Basic script to create a controlled agent by setting properly the 
* SecurityGrantsFolderProperty
* credentialsId :UBUNTU_SSH
* agent-node-2 :agent (agent name)
* /home/jenkins : /home/ubuntu (Remote root directory )
* agent-node-label : JAVA NODEJS (Lable)
*/

import hudson.model.*
import jenkins.model.*
import hudson.slaves.*
import com.cloudbees.hudson.plugins.folder.*
import com.cloudbees.jenkins.plugins.foldersplus.*
def parent=Jenkins.instance
Folder folder= Jenkins.instance.createProject(Folder.class, "Folder-A")
ComputerLauncher launcher = new hudson.plugins.sshslaves.SSHLauncher(
        "host", // Host
        22, // Port
        "UBUNTU_SSH", // Credentials
        (String)null, // JVM Options
        (String)null, // JavaPath
        (String)null, // Prefix Start Slave Command
        (String)null, // Suffix Start Slave Command
        (Integer)null, // Connection Timeout in Seconds
        (Integer)null, // Maximum Number of Retries
        (Integer)null // The number of seconds to wait between retries
)
// Define a "Permanent Agent"
Slave node = new DumbSlave(
        "agent-node-2",
        "/home/ubuntu",
        launcher)
node.nodeDescription = "Agent node description"
node.numExecutors = 1
node.labelString = "JAVA NODEJS Ansible"
node.mode = Node.Mode.NORMAL
node.retentionStrategy = new RetentionStrategy.Always()

// Create a "Permanent Agent"
Jenkins.instance.addNode(node)
node.getNodeProperties().add(new SecurityTokensNodeProperty(false));
SecurityToken token = SecurityToken.newInstance();
node.getNodeProperties().get(SecurityTokensNodeProperty.class).addSecurityToken(token);
node.save()
SecurityGrant request = SecurityGrant.newInstance();
SecurityGrant grant = token.issue(request);
folder.getProperties().replace(new SecurityGrantsFolderProperty(Collections.<SecurityGrant>emptyList()));
folder.getProperties().get(SecurityGrantsFolderProperty.class).addSecurityGrant(grant);
folder.save()
return "Node has been created successfully."
