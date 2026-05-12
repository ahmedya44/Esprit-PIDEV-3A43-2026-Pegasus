<?php

namespace App\Repository;

use App\Entity\Produit;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\QueryBuilder;
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

    /**
     * QueryBuilder pour la liste des produits (filtres + tri), utilisé avec la pagination.
     */
    public function getQueryBuilderForFilters(string $search = '', string $categorieId = '', string $tri = ''): QueryBuilder
    {
        $qb = $this->createQueryBuilder('p')
            ->leftJoin('p.categorie', 'c')
            ->andWhere("p.statut NOT IN ('en_attente', 'refuse')");

        if ($search !== '') {
            $qb->andWhere('p.nom LIKE :search OR p.description LIKE :search')
               ->setParameter('search', '%' . $search . '%');
        }

        if ($categorieId !== '') {
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

        return $qb;
    }

    public function findByFilters(string $search = '', string $categorieId = '', string $tri = ''): array
    {
        return $this->getQueryBuilderForFilters($search, $categorieId, $tri)->getQuery()->getResult();
    }

    /**
     * Returns catalog summary for the AI advisor (count + product names) — only visible products.
     */
    public function getCatalogSummary(int $maxNames = 30): array
    {
        $qb = $this->createQueryBuilder('p')
            ->andWhere("p.statut NOT IN ('en_attente', 'refuse')")
            ->orderBy('p.nom', 'ASC');

        $total = (int) (clone $qb)->select('COUNT(p.id)')->getQuery()->getSingleScalarResult();

        $rows = $qb->select('p.nom')
            ->setMaxResults($maxNames)
            ->getQuery()
            ->getArrayResult();
        $names = array_column($rows, 'nom');

        return ['total' => $total, 'names' => array_values($names)];
    }
}