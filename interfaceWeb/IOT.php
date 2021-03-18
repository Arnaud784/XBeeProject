<?php include('connexionBDD.php'); ?>

<html class="Sommaire">
	<head>
		<title>TP IOT</title>
		<meta content="text/html;charset=utf-8"/>
		<link rel="stylesheet" href="Style.css"> <!-- Feuille de style des sommaires -->
	</head>

	<body>
		<h1>Suivi des valeurs</h1><br><br><br><br><br><br>

		<table cellpadding="5" border="1" style="border-collapse: collapse; background-color: #F7A3F4;">
			<tr>
				<th>Date</th>
				<th>Source</th>
				<th>Valeur</th>
			</tr>
		<?php 
			// Exécution des requêtes SQL
			$query = "select * from mesures order by date desc LIMIT 50;";
			$mesures = $connexion->query($query);
			$mesures->setFetchMode(PDO::FETCH_OBJ);
			$mesure = $mesures->fetch();

			while ($mesure) {
				echo "<tr>";
					echo "<td><center>".$mesure->Date."</center></td>";
					echo "<td><center>".$mesure->Source."</center></td>";
					echo "<td><center>".$mesure->Valeur."</center></td>";
				echo "</tr>";

				$mesure = $mesures->fetch();
			}
		?>
	</body>
</html>