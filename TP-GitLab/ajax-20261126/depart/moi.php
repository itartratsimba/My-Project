<?php
// Retourne le membre connecté (401 si personne n'est connecté)
session_start();
header('Content-Type: application/json; charset=utf-8');

if (!isset($_SESSION['membre'])) {
    http_response_code(401);
    echo json_encode(['ok' => false, 'message' => 'Non connecté.']);
    exit;
}

echo json_encode(['ok' => true, 'membre' => $_SESSION['membre']]);
