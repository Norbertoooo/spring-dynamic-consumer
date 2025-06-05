import datetime

from airflow.decorators import dag
from airflow.decorators import task
from airflow.operators.empty import EmptyOperator


@dag(start_date=datetime.datetime(2021, 1, 1), schedule=None)
def generate_dag():

    EmptyOperator(task_id="task-one")

    @task()
    def task_two():
        return {"key": "value"}

    @task()
    def task_three():
        return {"key": "value"}

generate_dag()