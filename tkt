#!/bin/bash
#	tkt.sh
#		bash script for compiling, testing, and running (for the server) tickets

CP_gson="/Users/Howl/Documents/BYU/CS/xx_util/gson-2.8.0.jar:./app/src/main/java"

compile() {
	find ./app/src/main/java/tickets/server ./app/src/main/java/tickets/common -name "*.java" | xargs javac -cp $CP_gson -d .
	return 0
}

run_server() {
	java -cp $CP_gson tickets.server.ServerCommunicator
	return 0
}

test_server() {
	echo "testing server"
	return 0
}

test_client() {
	echo "testing client"
	return 0
}

test_common() {
	echo "testing common"
	return 0
}

print_usage() {
	echo "usage: ./tkt.sh [-r | -t (server|client|common) | -c]"
	echo "       -r : run the server"
	echo "       -t : run test cases..."
	echo "            -t server : test the server package"
	echo "            -t client : test the client package"
	echo "            -t common : test the common package"
	echo "       -c : compile all Android-independent files"
	return 0
}

if [ "$#" -eq 0 ]; then
	print_usage
	exit

elif [ $1 = "-r" ]; then
	run_server
	exit

elif [ $1 = "-t" ]; then

	if [ $# -ne 2 ]; then
		print_usage
		exit

	elif [ $2 = "server" ]; then
		test_server
		exit

	elif [ $2 = "client" ]; then
		test_client
		exit

	elif [ $2 = "common" ]; then
		test_common
		exit

	else
		print_usage
		exit

	fi

elif [ $1 = "-c" ]; then
	compile
	exit

else
	print_usage
	exit

fi


# project responsibilities
# 	canvas stuff and implementing the map class
# 	implement the longest route finding algorithm