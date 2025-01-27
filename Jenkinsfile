pipeline {
	agent {
		docker {
			image 'maven:3.9.3-eclipse-temurin-17'
			args '-v $HOME/.m2:/root/.m2'
		}
	}

	stages {
		stage('Build') {
			steps {
				echo 'Building..'
				sh 'mvn -B -DskipTests clean package'
			}
		}
		stage('Test') {
			steps {
				echo 'Testing..'
				sh 'mvn test'
			}
			post {
				always {
					junit 'target/surefire-reports/*.xml'
				}
			}
		}
		stage('Deploy') {
			steps {
				echo 'Deploying....'
			}
		}
	}
}