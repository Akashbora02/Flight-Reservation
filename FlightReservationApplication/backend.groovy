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
                    pwd
                    ls
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
                    mvn sonar:sonar -Dsonar.projectKey=Flight-Reservation
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
}