.PHONY: build run test clean

build:
	mvn clean package

run: build
	mvn exec:java -Dexec.mainClass=com.example.passwordgen.App

test:
	mvn test

clean:
	mvn clean
