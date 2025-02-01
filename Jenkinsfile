pipeline {
	agent any

	tools {
		jdk '21'
		maven 'maven'
	}

	stages {
		stage('Build') {
			steps {
				echo 'Building..'
				sh 'java -version'
				sh 'mvn -v'
				sh 'mvn -B -DskipTests clean package'
			}
		}
		stage('Test') {
			steps {
				echo 'Testing..'
				sh 'mvn test'
			}
		}
		stage('Deploy') {
			steps {
				echo 'Deploying....'
			}
		}
	}
}