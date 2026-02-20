pipeline{
    agent any
    stages{
        stage('PULL'){
            steps{
                git branch: 'main', url: 'https://github.com/Akashbora02/Flight-Reservation.git'
            }
        }
        stage('BUILD'){
            steps{
                sh'''
                    cd FlightReservationApplication
                    docker build -t akashbora02/flight-reservation-backend:latest .
                    docker push akashbora02/flight-reservation-backend:latest
                '''
            }
        }
        stage('TEST'){
            steps{
                    withSonarQubeEnv(credentialsId: 'sonar-token', installationName: 'sonar') {
                    sh'''
                    cd FlightReservationApplication
                    mvn clean verify sonar:sonar \
                      -Dsonar.projectKey=Flight-Reservation
                    '''
                    }
            }
        }
        stage('DEPLOY'){
            steps{
                sh '''
                    cd FlightReservationApplication
                    kubectl apply -f k8s/
                '''
            }
        }
    }
    post{
        success{
            echo "Pipeline executed successfully!"
        }
        failure{
            echo "Pipeline failed. Please check the logs for details."
        }
    }
}