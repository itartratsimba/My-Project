<?php
// Réponse JSON : {"ok":true,"nom":"..."} ou {"ok":false,"message":"..."}
session_start();
include("functions.php");
header('Content-Type: application/json; charset=utf-8');

$email = trim($_POST['email'] ?? '');
$pwd   = $_POST['pwd'] ?? '';

$membre = check_login($email, $pwd);

if ($membre) {
    $_SESSION['membre'] = $membre;
    echo json_encode(['ok' => true, 'nom' => $membre['nom']]);
} else {
    http_response_code(401);
    echo json_encode(['ok' => false, 'message' => 'Email ou mot de passe incorrect.']);
}
