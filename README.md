# sonar-hipchat-plugin
A sonar plugin to send notifications to a HipChat room.

## Howto ##
To build the plugin call **mvn clean package** (or download the current release). The artifact must be copied to the *SONAR_HOME/extensions/plugins* folder and sonarqube must be restarted.

Tested for sonarqube 5.2 and 5.3

## configuration ##
The following properties can be configured in the sonarqube build

    sonar.hipchat.disabled # true per default - disables the plugin completely
    sonar.hipchat.room # HipChat room where to broadcast the message
    sonar.hipchat.token # HipChat token for authentication
    sonar.hipchat.message # a message that is send to the HipChat room before the actual auto-generated message is sent
  
Currently a message in the following format is sent

    %s has been analysed at %s.%nStatus: %d new issues | %d resolved issues | %d total issues

With the first being the project name, the second the date of analyis.  
 
It is considered to offer a more flexible messaging though.
