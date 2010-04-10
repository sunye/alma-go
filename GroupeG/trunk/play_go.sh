#!/bin/bash

CP=
CLASS=alma.atarigo.AtariGo

for x in libs/*.jar
do
	CP="$CP:$x"
done

if mvn package 
then
	echo "Maven Build : Complete"
fi

java -cp "$CP:target/gogame-1.0.jar" $CLASS
