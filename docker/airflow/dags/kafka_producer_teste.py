from airflow import DAG
from airflow.operators.dummy import DummyOperator
from datetime import datetime

with DAG(
    dag_id="dag_teste",
    start_date=datetime(2024, 1, 1),
    schedule_interval="@daily",
    catchup=False
) as dag:
    tarefa = DummyOperator(task_id="tarefa_teste")