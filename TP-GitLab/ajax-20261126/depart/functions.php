<?php
include_once("connection.php");

// Protège les textes saisis avant de les mettre dans une requête SQL
function esc($texte)
{
    return mysqli_real_escape_string(dbconnect(), $texte);
}

// ---------- Membres ----------

// Retourne le membre correspondant à l'email, ou null s'il n'existe pas
function get_membre_by_email($email)
{
    $sql = "SELECT * FROM membre WHERE email='%s'";
    $sql = sprintf($sql, esc($email));
    $req = mysqli_query(dbconnect(), $sql);
    $result = mysqli_fetch_assoc($req);
    mysqli_free_result($req);
    return $result;
}

// Vérifie email + mot de passe. Retourne le membre (sans pwd) ou false.
function check_login($email, $pwd)
{
    $membre = get_membre_by_email($email);
    if ($membre && password_verify($pwd, $membre["pwd"])) {
        unset($membre["pwd"]);
        return $membre;
    }
    return false;
}
