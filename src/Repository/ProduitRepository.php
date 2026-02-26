<?php

namespace App\Repository;

use App\Entity\Produit;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Produit>
 */
class ProduitRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Produit::class);
    }

    public function findByFilters(string $search = '', string $categorieId = '', string $tri = ''): array
    {
        $qb = $this->createQueryBuilder('p')
            ->leftJoin('p.categorie', 'c');

        if ($search) {
            $qb->andWhere('p.nom LIKE :search OR p.description LIKE :search')
               ->setParameter('search', '%' . $search . '%');
        }

        if ($categorieId) {
            $qb->andWhere('c.id = :categorieId')
               ->setParameter('categorieId', $categorieId);
        }

        switch ($tri) {
            case 'prix_asc':
                $qb->orderBy('p.prix', 'ASC');
                break;
            case 'prix_desc':
                $qb->orderBy('p.prix', 'DESC');
                break;
            case 'nom_asc':
                $qb->orderBy('p.nom', 'ASC');
                break;
            case 'nom_desc':
                $qb->orderBy('p.nom', 'DESC');
                break;
            default:
                $qb->orderBy('p.id', 'DESC');
        }

        return $qb->getQuery()->getResult();
    }
}