<?php

namespace App\Service;

use App\Entity\Commande;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Mime\Part\DataPart;

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

    /**
     * Envoie le ticket/facture PDF par email au client.
     * Utiliser après un paiement réussi si vous avez l'email du client.
     */
    public function sendTicketParEmail(string $destinataire, Commande $commande, string $pdfContent): void
    {
        $filename = sprintf('ticket-commande-%d.pdf', $commande->getId());
        $email = (new Email())
            ->from('pegasus@artiste.com')
            ->to($destinataire)
            ->subject('Votre ticket de caisse - Commande #' . $commande->getId() . ' - Pegasus')
            ->html('
                <h2>Votre commande #' . $commande->getId() . '</h2>
                <p>Veuillez trouver ci-joint votre ticket de caisse / facture au format PDF.</p>
                <p>Total : <strong>' . number_format($commande->getTotal(), 2, ',', ' ') . ' €</strong></p>
                <br>
                <p>L\'équipe Pegasus</p>
            ')
            ->attach($pdfContent, $filename, 'application/pdf');

        $this->mailer->send($email);
    }
}