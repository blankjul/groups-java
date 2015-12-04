mvn compile assembly:single
mvn dependency:copy-dependencies
mvn package

rm bin -R
mkdir bin
cp target/clique-0.0.1-SNAPSHOT.jar bin
cp target/clique-0.0.1-SNAPSHOT-jar-with-dependencies.jar bin
cp target/dependency bin/libs -R

#java -cp moo-0.1.jar:lib/* 
#java -jar moo-0.1-jar-with-dependencies.jar

