default: build run clean 

build:
	@echo "`tput bold`Building...`tput sgr0`"
	@javac lab1/Main.java

run:
	@echo "`tput bold`Output:`tput sgr0`"
	@java lab1/Main 

clean:
	@echo "`tput bold`Cleaning...`tput sgr0`"
	@rm -rfv lab1/*.class lab1/*/*.class lab1/*/*/*.class