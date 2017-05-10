* mvn clean install -Dmaven.test.skip=true
* cf push rabbit-example -p target/rabbitmq-example-0.0.1-SNAPSHOT.jar --no-start
* cf bs rabbit-example rabbitmq-service
* cf set-env rabbit-example rabbitmq.service.name rabbitmq-service
* cf set-env rabbit-example queue.name test-queue
* cf start rabbit-example

* Go to Apps Manager > Select the application > click the services > rabbitmq-service > Manage
* Create a queue called `test-queue`

* Access the endpoint:
	- http://rabbit-example.local.pcfdev.io | POST | {"body":"Some message"}
	- http://rabbit-example.local.pcfdev.io | GET | Should get the message from the queue