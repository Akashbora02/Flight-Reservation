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
                    cd frontend
                    npm install
                    npm run build
                    '''
            }
        }
        stage('DEPLOY'){
            steps{
                sh '''
                    cd frontend
                    aws s3 sync dist/ s3://flight-reservation-bucket-121 --delete
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