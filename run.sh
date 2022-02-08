#The contents of this file are run in the
# production environment after a new version is deployed

#Stop running the old version of the project
ps ax | awk '/[D]iscordBot/ { print $1 }' | xargs kill
#Navigate into the project directory
cd /home/ec2-user/DiscordBot_v2
#Assembling all outputs and running all checks
./gradlew clean build
#Run the project
nohup ./gradlew run > /dev/null 2> /dev/null < /dev/null &
