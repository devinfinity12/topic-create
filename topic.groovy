pipeline {
    agent any

    environment {
        KAFKA_BIN = '/etc/confluent-7.8.0/bin' // Replace with the correct path to Kafka binaries
        KAFKA_BROKER = 'localhost:9092' // Kafka broker address
        TOPIC_NAME = 'my_new_topic-dev' // Topic name to create
        PARTITIONS = 3 // Number of partitions
        REPLICATION_FACTOR = 1 // Replication factor
    }

    stages {
        stage('Create Kafka Topic') {
            steps {
                script {
                    // Create Kafka topic using kafka-topics.sh command
                    def createTopicCmd = """
                        ${KAFKA_BIN}/kafka-topics --create --bootstrap-server ${KAFKA_BROKER} --replication-factor ${REPLICATION_FACTOR} --partitions ${PARTITIONS} --topic ${TOPIC_NAME}
                    """
                    echo "Running Kafka Topic creation command: ${createTopicCmd}"
                    sh createTopicCmd
                }
            }
        }
    }

    post {
        success {
            echo "Kafka topic '${TOPIC_NAME}' created successfully."
        }
        failure {
            echo "Failed to create Kafka topic '${TOPIC_NAME}'."
        }
    }
}
