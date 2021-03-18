<?php
include('connexionBDD.php');

$limit = 1;
if(isset($_GET['limit'])) {
	$limit = $_GET['limit'];
}

$query = "select * from mesures ORDER BY Date DESC LIMIT $limit ;";
$mesures = $connexion->query($query);
$mesures->setFetchMode(PDO::FETCH_OBJ);
$mesure = $mesures->fetch();

while ($mesure) {
	echo $mesure->Date."/";
	echo $mesure->Source."/";
	echo $mesure->Valeur;

	$mesure = $mesures->fetch();
}
?>