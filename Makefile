make cm:
	mvn clean install && start cmd.exe /c java -jar target\javashell-0.0.3-SNAPSHOT.jar
make start:
	start cmd.exe /c java -jar target\javashell-0.0.3-SNAPSHOT.jar