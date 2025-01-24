<?php
// Conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$database = "practica5";

$conn = new mysqli($servername, $username, $password, $database);

// Verifica la conexión
if ($conn->connect_error) {
    die(json_encode(["status" => false, "message" => "Error de conexión: " . $conn->connect_error]));
}

// Manejo de solicitudes
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Añadir un ave
    $ave = $_POST['ave'] ?? null;
    $lugar = $_POST['lugar'] ?? null;
    $fecha = $_POST['fecha'] ?? null;

    if (!$ave || !$lugar || !$fecha) {
        echo json_encode(["status" => false, "message" => "Todos los campos son obligatorios"]);
        exit();
    }

    $query = "INSERT INTO aves (nombre, lugar, fecha) VALUES (?, ?, ?)";
    $stmt = $conn->prepare($query);
    $stmt->bind_param("sss", $ave, $lugar, $fecha);

    if ($stmt->execute()) {
        echo json_encode(["status" => true, "message" => "Ave registrada correctamente"]);
    } else {
        echo json_encode(["status" => false, "message" => "Error al registrar el ave"]);
    }

    $stmt->close();
} elseif ($_SERVER['REQUEST_METHOD'] === 'GET') {
    // Ver aves
    $query = "SELECT nombre AS ave, lugar, fecha FROM aves";
    $result = $conn->query($query);

    $aves = [];
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $aves[] = $row;
        }
    }

    echo json_encode($aves);
}

$conn->close();
?>