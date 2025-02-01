pipeline {
	agent any

	tools {
		jdk '21'
	}

	stages {
		stage('Build') {
			steps {
				withMaven(jdk: '21') {
					echo 'Building..'
					sh 'mvn -B -DskipTests clean package'
				}
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