# 203443601 203392964
# Cohenoh2 kleinid1

# create folder for binaries
bin:
	mkdir bin

# compile many java files by providing the java compiler a file containing 
# many file names in different directories
compile: bin
	find src | grep .java > files_to_compile.txt 
	javac -d bin -cp biuoop-1.4.jar @files_to_compile.txt

# Create a jar with a manifest, that defines that the main class is Ass6Game
jar:
	jar cfm ass6game.jar manifest.mf -C bin . -C resources .

# Run a jar with a manifest, the classes in the jar will be added to the classpath and the
# decision where to find the main that should run is based on the 'Main-Class' property
# in the manifest
run-jar: 
	java -jar ass6game.jar

# Run without a jar
run:
	java -cp bin:resources:biuoop-1.4.jar Ass6Game
