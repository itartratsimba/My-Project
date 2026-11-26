<?php
// Connexion MySQL : adapter les 4 valeurs ci-dessous à votre serveur
function dbconnect()
{
    static $connect = null;
    if ($connect === null) {
        $connect = mysqli_connect('localhost', 'root', '', 'tp_ajax');
        if (!$connect) {
            http_response_code(500);
            header('Content-Type: application/json; charset=utf-8');
            echo json_encode(['ok' => false, 'message' => 'Connexion à la base impossible.']);
            exit;
        }
        mysqli_set_charset($connect, 'utf8mb4');
    }
    return $connect;
}
