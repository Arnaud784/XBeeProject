<?php
include('connexionBDD.php');

$query = "select * from mesures ORDER BY Date DESC LIMIT 30 ;";
$mesures = $connexion->query($query);
$mesures->setFetchMode(PDO::FETCH_OBJ);
$mesure = $mesures->fetch();

?>
<html>
<header>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js" integrity="sha512-d9xgZrVZpmmQlfonhQUvTR7lMPtO7NkZMkA0ABN3PHCbKA5nqylQ/yWlFAyY6hYgdF1Qh6nYiuADWwKB4C2WSw==" crossorigin="anonymous"></script>
</header>
<canvas id="myChart"></canvas>
<script>
var dates = [];
var valeurs = [];
var sources = [];
var lastDate = "";
var lastValeur = 0;

<?php
echo 'lastDate = "'.$mesure->Date.'";'.PHP_EOL;
echo 'lastValeur = '.$mesure->Valeur.';'.PHP_EOL;
while ($mesure) {
	echo 'dates.unshift("'.$mesure->Date.'");'.PHP_EOL;
	echo 'valeurs.unshift('.$mesure->Valeur.');'.PHP_EOL;
	echo 'sources.unshift("'.$mesure->Source.'");'.PHP_EOL;

	$mesure = $mesures->fetch();
}
?>

var ctx = document.getElementById('myChart').getContext('2d');
var chart = new Chart(ctx, {
    // The type of chart we want to create
    type: 'line',

    // The data for our dataset
    data: {
        labels: dates,
        datasets: [{
            label: 'Plante 1',
            backgroundColor: 'rgb(132, 99, 255)',
            borderColor: 'rgb(132, 99, 255)',
            data: valeurs
        }]
    },

    // Configuration options go here
    options: {}
});

function addData(chart, label, data) {
    chart.data.labels.push(label);
    chart.data.datasets.forEach((dataset) => {
        dataset.data.push(data);
    });
    chart.update();
}

function removeData(chart) {
    chart.data.labels.pop();
    chart.data.datasets.forEach((dataset) => {
        dataset.data.shift();
    });
    chart.update();
}

function showCustomer(limit) {
  console.log("show");
  var xhttp;
  xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
    //document.getElementById("txtHint").innerHTML = this.responseText;
		console.log("response : "+this.responseText);
		var splitedResponse = this.responseText.split('/');
		console.log("splited");
		console.log(splitedResponse[0]);
		console.log(splitedResponse[2]);
		console.log(lastDate);
		if(lastDate != splitedResponse[0]) {
			console.log("modif");
			lastDate = splitedResponse[0];
			lastValeur = parseInt(splitedResponse[2]);
			addData(chart, splitedResponse[0], splitedResponse[2]);
			removeData(chart)
		}
    }
  };
  xhttp.open("GET", "getdata.php?limit="+limit, true);
  xhttp.send();
  //setTimeout(showCustomer(limit), 3000);
}
const interval = setInterval(function() {
	showCustomer(1)
   // method to be executed;
 }, 2000);

//addData(chart, "test", 8);
</script>
</html>