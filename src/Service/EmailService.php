<?php

namespace App\Service;

use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;

class EmailService
{
    public function __construct(private MailerInterface $mailer)
    {
    }

    public function sendProduitAccepte(string $destinataire, string $nomProduit): void
    {
        $email = (new Email())
            ->from('pegasus@artiste.com')
            ->to($destinataire)
            ->subject('Votre produit a été accepté - Pegasus')
            ->html('
                <h2>Félicitations !</h2>
                <p>Votre produit <strong>' . $nomProduit . '</strong> a été <span style="color: green;">accepté</span> par notre équipe.</p>
                <p>Il est maintenant visible sur la plateforme Pegasus.</p>
                <br>
                <p>L\'équipe Pegasus</p>
            ');

        $this->mailer->send($email);
    }

    public function sendProduitRefuse(string $destinataire, string $nomProduit, string $message = ''): void
    {
        $email = (new Email())
            ->from('pegasus@artiste.com')
            ->to($destinataire)
            ->subject('Votre produit a été refusé - Pegasus')
            ->html('
                <h2>Information concernant votre produit</h2>
                <p>Votre produit <strong>' . $nomProduit . '</strong> a été <span style="color: red;">refusé</span>.</p>
                ' . ($message ? '<p>Raison : ' . $message . '</p>' : '') . '
                <p>Vous pouvez modifier votre produit et le soumettre à nouveau.</p>
                <br>
                <p>L\'équipe Pegasus</p>
            ');

        $this->mailer->send($email);
    }

    public function sendNouvelleCommande(string $destinataire, int $commandeId, float $total): void
    {
        $email = (new Email())
            ->from('pegasus@artiste.com')
            ->to($destinataire)
            ->subject('Nouvelle commande #' . $commandeId . ' - Pegasus')
            ->html('
                <h2>Nouvelle commande reçue !</h2>
                <p>Vous avez reçu une nouvelle commande <strong>#' . $commandeId . '</strong>.</p>
                <p>Total : <strong>' . $total . ' €</strong></p>
                <br>
                <p>L\'équipe Pegasus</p>
            ');

        $this->mailer->send($email);
    }
}