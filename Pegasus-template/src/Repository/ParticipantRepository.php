<?php

namespace App\Repository;

use App\Entity\Participant;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Participant>
 *
 * @method Participant|null find($id, $lockMode = null, $lockVersion = null)
 * @method Participant|null findOneBy(array $criteria, array $orderBy = null)
 * @method Participant[]    findAll()
 * @method Participant[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ParticipantRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Participant::class);
    }

    /**
     * @return Participant[]
     */
    public function findAllSortedByEvent(?int $evenementId = null): array
    {
        $qb = $this->createQueryBuilder('p')
            ->leftJoin('p.evenement', 'e')
            ->orderBy('e.titre', 'ASC')
            ->addOrderBy('p.nom', 'ASC');

        if ($evenementId) {
            $qb->andWhere('e.id = :evenementId')
               ->setParameter('evenementId', $evenementId);
        }

        return $qb->getQuery()->getResult();
    }
}
