pipeline {
	agent {
		dockerContainer {
			image 'maven:3.9.9-eclipse-temurin-21-alpine'
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