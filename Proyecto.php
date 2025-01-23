<?php
// Conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$database = "practica5";

$conn = new mysqli($servername, $username, $password, $database);

// Verifica la conexión
if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Error de conexión: " . $conn->connect_error]));
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $nombre = $_POST['nombre'] ?? '';
    $contrasenia_actual = $_POST['contrasenia_actual'] ?? '';
    $nueva_contrasenia = $_POST['nueva_contrasenia'] ?? '';

    if (empty($nombre) || empty($contrasenia_actual) || empty($nueva_contrasenia)) {
        echo json_encode(["status" => "error", "message" => "Todos los campos son obligatorios"]);
        exit();
    }

    $query = "SELECT * FROM usuarios WHERE nombre = ? AND contrasenia = ?";
    $stmt = $conn->prepare($query);
    $stmt->bind_param("ss", $nombre, $contrasenia_actual);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $query_update = "UPDATE usuarios SET contrasenia = ? WHERE nombre = ?";
        $stmt_update = $conn->prepare($query_update);
        $stmt_update->bind_param("ss", $nueva_contrasenia, $nombre);

        if ($stmt_update->execute()) {
            echo json_encode(["status" => "success", "message" => "Contraseña actualizada correctamente"]);
        } else {
            echo json_encode(["status" => "error", "message" => "Error al actualizar la contraseña"]);
        }
        $stmt_update->close();
    } else {
        echo json_encode(["status" => "error", "message" => "Usuario o contraseña actual incorrectos"]);
    }
    $stmt->close();
}
$conn->close();
?>
