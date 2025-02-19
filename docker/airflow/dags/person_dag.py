import datetime

from airflow import DAG
from airflow.operators.empty import EmptyOperator
from airflow.operators.bash import BashOperator
from airflow.providers.common.sql.operators.sql import SQLExecuteQueryOperator
from airflow.providers.apache.kafka.operators.produce import ProduceToTopicOperator
from airflow.operators.python_operator import PythonOperator

oracle_conn_id = 'oracle_conn'

def push_sql_results(**kwargs):
    ti = kwargs['ti']
    result = kwargs['task_instance'].xcom_pull(task_ids='terceira_task')
    ti.xcom_push(key='sql_data', value=result)

def producer_function(**kwargs):
    ti = kwargs['ti']
    xcom_value = ti.xcom_pull(task_ids="quarta_task", key="sql_data")
    print(f"Mensagem recebida: {xcom_value}")  # Será exibido nos logs do Airflow
    return mensagem

person_dag = DAG(
    dag_id="person_dag",
    start_date=datetime.datetime(2021, 1, 1),
    schedule=None,
)

sql_query = """
    SELECT * FROM airflow.person
"""

primeira_task = EmptyOperator(task_id="primeira_task", dag=person_dag)
segunda_task = BashOperator(task_id="segunda_task", bash_command='echo "essa merda de query"', retries=2)
terceira_task = SQLExecuteQueryOperator(task_id="terceira_task", do_xcom_push=True, sql="SELECT * FROM airflow.person", conn_id=oracle_conn_id,show_return_value_in_logs='True')
quarta_task = PythonOperator(task_id="quarta_task",python_callable=push_sql_results,provide_context=True)
quinta_task = ProduceToTopicOperator(kafka_config_id="kafka_conn",task_id="quinta_task",topic="person.topic",producer_function=producer_function)


primeira_task >> segunda_task >> terceira_task >> quarta_task >> quinta_task