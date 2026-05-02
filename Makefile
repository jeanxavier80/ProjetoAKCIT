.PHONY: build run test clean

build:
	mvn clean package

run: build
	mvn exec:java -Dexec.mainClass=com.example.passwordgen.App

test:
	mvn test

e2e:
	powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\e2e-test.ps1

clean:
	mvn clean
