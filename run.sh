cd /home/ec2-user/DiscordBot_v2
./gradlew clean build
./gradlew shadowJar
cd ./build/libs
java -jar discordbot-2.0-all.jar
