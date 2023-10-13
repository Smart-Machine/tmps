default: build run clean 

build:
	@echo "`tput bold`Building...`tput sgr0`"
	@javac lab2/Main.java

run:
	@echo "`tput bold`Output:`tput sgr0`"
	@java lab2/Main 

clean:
	@echo "`tput bold`Cleaning...`tput sgr0`"
	@rm -rfv lab2/*.class lab2/*/*.class lab2/*/*/*.class