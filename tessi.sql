-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 29 août 2023 à 15:14
-- Version du serveur : 10.4.24-MariaDB
-- Version de PHP : 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `tessi`
--

-- --------------------------------------------------------

--
-- Structure de la table `autorisation`
--

CREATE TABLE `autorisation` (
  `id_autorisation` int(11) NOT NULL,
  `date_debut` datetime(6) DEFAULT NULL,
  `date_fin` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `demandeur_id_emp` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `autorisation`
--

INSERT INTO `autorisation` (`id_autorisation`, `date_debut`, `date_fin`, `description`, `demandeur_id_emp`) VALUES
(2, '2023-08-28 15:28:20.000000', '2023-08-28 17:28:20.000000', 'asazd', 1);

-- --------------------------------------------------------

--
-- Structure de la table `conge`
--

CREATE TABLE `conge` (
  `id_conge` int(11) NOT NULL,
  `date_debut` datetime(6) DEFAULT NULL,
  `date_demande` datetime(6) DEFAULT NULL,
  `date_fin` datetime(6) DEFAULT NULL,
  `date_validation` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duree` int(11) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `piece_jointe` longblob DEFAULT NULL,
  `demandeur_id_emp` int(11) DEFAULT NULL,
  `motif_refus_id_motif` int(11) DEFAULT NULL,
  `type_conge_id_type_conge` int(11) DEFAULT NULL,
  `validateur_id_emp` int(11) DEFAULT NULL,
  `id_delegue` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `conge`
--

INSERT INTO `conge` (`id_conge`, `date_debut`, `date_demande`, `date_fin`, `date_validation`, `description`, `duree`, `etat`, `piece_jointe`, `demandeur_id_emp`, `motif_refus_id_motif`, `type_conge_id_type_conge`, `validateur_id_emp`, `id_delegue`) VALUES
(1, '2023-07-27 09:43:11.000000', '2023-07-19 09:43:11.000000', '2023-07-29 09:43:11.000000', '2023-07-26 15:29:06.000000', 'Congé', 0, 'Accepte', NULL, 2, 1, 1, 1, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `employee`
--

CREATE TABLE `employee` (
  `id_emp` int(11) NOT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `entite` varchar(255) DEFAULT NULL,
  `mot_de_pass` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `statut` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `politique_id_politique` int(11) DEFAULT NULL,
  `role_id_role` int(11) DEFAULT NULL,
  `superviseur_id_emp` int(11) DEFAULT NULL,
  `autorisation_duration` bigint(20) NOT NULL,
  `autorisation_jour` bit(1) NOT NULL,
  `solde_conge` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `employee`
--

INSERT INTO `employee` (`id_emp`, `adresse`, `email`, `entite`, `mot_de_pass`, `nom`, `prenom`, `statut`, `username`, `politique_id_politique`, `role_id_role`, `superviseur_id_emp`, `autorisation_duration`, `autorisation_jour`, `solde_conge`) VALUES
(1, 'Ben arous', 'achref.zitoun@esprit.tn', 'Développement ', 'achref789', 'Achref', 'Zitoun', 'Actif', 'achrefzitoun789', 1, 42, 1, 2, b'1', 40.5),
(2, 'Mourouj', 'achref.zitoun@esprit.tn', 'Développement', 'chaima789', 'Chaima', 'HadjKacem', 'Actif', 'Chaimahadjkacem789', 1, 42, 1, 6, b'0', 50);

-- --------------------------------------------------------

--
-- Structure de la table `jour_ferie`
--

CREATE TABLE `jour_ferie` (
  `id_jour` int(11) NOT NULL,
  `annee` int(6) DEFAULT NULL,
  `date_debut` datetime(6) DEFAULT NULL,
  `date_fin` datetime(6) DEFAULT NULL,
  `nom_date` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `jour_ferie`
--

INSERT INTO `jour_ferie` (`id_jour`, `annee`, `date_debut`, `date_fin`, `nom_date`) VALUES
(36, 2024, '2024-01-01 00:00:00.000000', '2024-01-01 23:59:59.000000', 'Jour de l\'an'),
(37, 2024, '2024-04-17 00:00:00.000000', '2024-04-17 23:59:59.000000', 'Lundi de Pâques'),
(38, 2024, '2024-05-01 00:00:00.000000', '2024-05-01 23:59:59.000000', 'Fête du Travail'),
(39, 2024, '2024-05-08 00:00:00.000000', '2024-05-08 23:59:59.000000', 'Victoire des Alliés'),
(40, 2024, '2024-05-25 00:00:00.000000', '2024-05-25 23:59:59.000000', 'Ascension'),
(41, 2024, '2024-06-05 00:00:00.000000', '2024-06-05 23:59:59.000000', 'Lundi de Pentecôte'),
(42, 2024, '2024-07-14 00:00:00.000000', '2024-07-14 23:59:59.000000', 'Fête Nationale'),
(43, 2024, '2024-08-15 00:00:00.000000', '2024-08-15 23:59:59.000000', 'Assomption'),
(44, 2024, '2024-11-01 00:00:00.000000', '2024-11-01 23:59:59.000000', 'La Toussaint'),
(45, 2024, '2024-11-11 00:00:00.000000', '2024-11-11 23:59:59.000000', 'Armistice'),
(46, 2024, '2024-12-25 00:00:00.000000', '2024-12-25 23:59:59.000000', 'Noël'),
(47, 2024, '2024-01-01 00:00:00.000000', '2024-01-01 23:59:59.000000', 'Jour de l\'an'),
(48, 2024, '2024-01-14 00:00:00.000000', '2024-01-14 23:59:59.000000', 'Fête de la Révolution et de la jeunesse'),
(49, 2024, '2024-03-20 00:00:00.000000', '2024-03-20 23:59:59.000000', 'Fête de l\'Indépendance de la Tunisie'),
(50, 2024, '2024-04-09 00:00:00.000000', '2024-04-09 23:59:59.000000', 'Journée des Martyrs en Tunisie'),
(51, 2024, '2024-05-01 00:00:00.000000', '2024-05-01 23:59:59.000000', 'Fête du Travail'),
(52, 2024, '2024-07-25 00:00:00.000000', '2024-07-25 23:59:59.000000', 'Fête de la République en Tunisie'),
(53, 2024, '2024-08-13 00:00:00.000000', '2024-08-13 23:59:59.000000', 'Fête de la femme et de la famille en Tunisie'),
(54, 2024, '2024-10-15 00:00:00.000000', '2024-10-15 23:59:59.000000', 'Fête de l\'évacuation'),
(55, 2023, '2023-08-13 00:00:00.000000', '2023-08-13 23:59:59.000000', 'Fête de la femme et de la famille en Tunisie'),
(56, 2023, '2023-10-15 00:00:00.000000', '2023-10-15 23:59:59.000000', 'Fête de l\'évacuation');

-- --------------------------------------------------------

--
-- Structure de la table `jour_ferie_politiques`
--

CREATE TABLE `jour_ferie_politiques` (
  `jour_ferie_id_jour` int(11) NOT NULL,
  `politiques_id_politique` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `jour_ferie_politiques`
--

INSERT INTO `jour_ferie_politiques` (`jour_ferie_id_jour`, `politiques_id_politique`) VALUES
(47, 1),
(48, 1),
(49, 1),
(50, 1),
(51, 1),
(52, 1),
(53, 1),
(54, 1),
(49, 1),
(50, 1),
(51, 1),
(52, 1),
(53, 1),
(54, 1),
(56, 1),
(55, 1),
(36, 2),
(37, 2),
(38, 2),
(39, 2),
(40, 2),
(41, 2),
(42, 2),
(43, 2),
(44, 2),
(45, 2),
(46, 2);

-- --------------------------------------------------------

--
-- Structure de la table `motif_refus`
--

CREATE TABLE `motif_refus` (
  `id_motif` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type_motif` varchar(255) DEFAULT NULL,
  `nom_motif` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `motif_refus`
--

INSERT INTO `motif_refus` (`id_motif`, `description`, `type_motif`, `nom_motif`) VALUES
(1, 'Parceque', 'Statique', 't'),
(7, 'hahhaha', 'Autre', 'a'),
(8, 'Autre', 'Autre', 't'),
(9, 'test', 'Autre', 'a');

-- --------------------------------------------------------

--
-- Structure de la table `politique`
--

CREATE TABLE `politique` (
  `id_politique` int(11) NOT NULL,
  `description_politique` varchar(255) DEFAULT NULL,
  `nom_politique` varchar(255) DEFAULT NULL,
  `nombre_jour_ouvrable` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `politique`
--

INSERT INTO `politique` (`id_politique`, `description_politique`, `nom_politique`, `nombre_jour_ouvrable`) VALUES
(1, 'adazd', 'Tunisienne', 2),
(2, 'test', 'Française', 10);

-- --------------------------------------------------------

--
-- Structure de la table `politique_type_conge`
--

CREATE TABLE `politique_type_conge` (
  `politique_id_politique` int(11) NOT NULL,
  `type_conge_id_type_conge` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `politique_type_conge`
--

INSERT INTO `politique_type_conge` (`politique_id_politique`, `type_conge_id_type_conge`) VALUES
(1, 2),
(1, 3),
(1, 6),
(1, 4),
(1, 7),
(1, 8),
(1, 9),
(1, 1),
(1, 1),
(1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

CREATE TABLE `role` (
  `id_role` int(11) NOT NULL,
  `description` text DEFAULT NULL,
  `nom_role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `role`
--

INSERT INTO `role` (`id_role`, `description`, `nom_role`) VALUES
(42, 'admin', 'admin'),
(44, 'Les tâches du superviseur incluent la répartition des tâches, la surveillance des performances, la communication avec la direction et la résolution des problèmes au niveau opérationnel.', 'superviseur'),
(45, 'employee', 'employee');

-- --------------------------------------------------------

--
-- Structure de la table `type_conge`
--

CREATE TABLE `type_conge` (
  `id_type_conge` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `nom_type` varchar(255) DEFAULT NULL,
  `nbr_jours` float DEFAULT NULL,
  `nature_type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `type_conge`
--

INSERT INTO `type_conge` (`id_type_conge`, `description`, `nom_type`, `nbr_jours`, `nature_type`) VALUES
(1, 'Paternite', 'Conge de paternite', 3, 'Speciale'),
(2, 'maternite', 'Conge de maternite', 60, 'Speciale'),
(3, 'sans solde', 'Conge sans solde', 90, 'Speciale'),
(4, 'mariage', 'Conge de mariage', 3, 'Speciale'),
(6, 'normale', 'Conge normale', 0, 'Normale'),
(7, 'Maladie', 'Conge de maladie', 0, 'Speciale'),
(8, 'normale', 'Demi jour matin', 0.5, 'Normale'),
(9, 'normale', 'Demi jour après midi', 0.5, 'Normale');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `autorisation`
--
ALTER TABLE `autorisation`
  ADD PRIMARY KEY (`id_autorisation`),
  ADD KEY `FK3juu5kylyh4pjn9w89c9n8og8` (`demandeur_id_emp`);

--
-- Index pour la table `conge`
--
ALTER TABLE `conge`
  ADD PRIMARY KEY (`id_conge`),
  ADD KEY `FKdg0kd7wfw6x9vp96jdhodiuc6` (`demandeur_id_emp`),
  ADD KEY `FK5ec7u09hub6jsx4alvgiv1gn0` (`motif_refus_id_motif`),
  ADD KEY `FKh6opopmuj8e5l7k465728qfx7` (`type_conge_id_type_conge`),
  ADD KEY `FKrwceg1m131bux1w1ccwtvqv0y` (`validateur_id_emp`);

--
-- Index pour la table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id_emp`),
  ADD KEY `FKb0jiyk3haqt874kwn61uxfwid` (`politique_id_politique`),
  ADD KEY `FKflnrbfssr57pwesw8x1hml2ga` (`role_id_role`),
  ADD KEY `FK8192kiahi01e3jmhoc7pkl5yu` (`superviseur_id_emp`);

--
-- Index pour la table `jour_ferie`
--
ALTER TABLE `jour_ferie`
  ADD PRIMARY KEY (`id_jour`);

--
-- Index pour la table `jour_ferie_politiques`
--
ALTER TABLE `jour_ferie_politiques`
  ADD KEY `FKdiftf3k88hrfm8houoqb0bfnx` (`politiques_id_politique`),
  ADD KEY `FKanv6394c7na3oqdm38nebatgf` (`jour_ferie_id_jour`);

--
-- Index pour la table `motif_refus`
--
ALTER TABLE `motif_refus`
  ADD PRIMARY KEY (`id_motif`);

--
-- Index pour la table `politique`
--
ALTER TABLE `politique`
  ADD PRIMARY KEY (`id_politique`);

--
-- Index pour la table `politique_type_conge`
--
ALTER TABLE `politique_type_conge`
  ADD KEY `FK34qpdmpg1qbix52dwldqwxgwa` (`type_conge_id_type_conge`),
  ADD KEY `FKc1boxmqx8ry77n6pgad1gmw8i` (`politique_id_politique`);

--
-- Index pour la table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id_role`);

--
-- Index pour la table `type_conge`
--
ALTER TABLE `type_conge`
  ADD PRIMARY KEY (`id_type_conge`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `autorisation`
--
ALTER TABLE `autorisation`
  MODIFY `id_autorisation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `conge`
--
ALTER TABLE `conge`
  MODIFY `id_conge` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=96;

--
-- AUTO_INCREMENT pour la table `employee`
--
ALTER TABLE `employee`
  MODIFY `id_emp` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `jour_ferie`
--
ALTER TABLE `jour_ferie`
  MODIFY `id_jour` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT pour la table `motif_refus`
--
ALTER TABLE `motif_refus`
  MODIFY `id_motif` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `politique`
--
ALTER TABLE `politique`
  MODIFY `id_politique` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `role`
--
ALTER TABLE `role`
  MODIFY `id_role` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT pour la table `type_conge`
--
ALTER TABLE `type_conge`
  MODIFY `id_type_conge` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `autorisation`
--
ALTER TABLE `autorisation`
  ADD CONSTRAINT `FK3juu5kylyh4pjn9w89c9n8og8` FOREIGN KEY (`demandeur_id_emp`) REFERENCES `employee` (`id_emp`);

--
-- Contraintes pour la table `conge`
--
ALTER TABLE `conge`
  ADD CONSTRAINT `FK5ec7u09hub6jsx4alvgiv1gn0` FOREIGN KEY (`motif_refus_id_motif`) REFERENCES `motif_refus` (`id_motif`),
  ADD CONSTRAINT `FKdg0kd7wfw6x9vp96jdhodiuc6` FOREIGN KEY (`demandeur_id_emp`) REFERENCES `employee` (`id_emp`),
  ADD CONSTRAINT `FKh6opopmuj8e5l7k465728qfx7` FOREIGN KEY (`type_conge_id_type_conge`) REFERENCES `type_conge` (`id_type_conge`),
  ADD CONSTRAINT `FKrwceg1m131bux1w1ccwtvqv0y` FOREIGN KEY (`validateur_id_emp`) REFERENCES `employee` (`id_emp`);

--
-- Contraintes pour la table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `FK8192kiahi01e3jmhoc7pkl5yu` FOREIGN KEY (`superviseur_id_emp`) REFERENCES `employee` (`id_emp`),
  ADD CONSTRAINT `FKb0jiyk3haqt874kwn61uxfwid` FOREIGN KEY (`politique_id_politique`) REFERENCES `politique` (`id_politique`),
  ADD CONSTRAINT `FKflnrbfssr57pwesw8x1hml2ga` FOREIGN KEY (`role_id_role`) REFERENCES `role` (`id_role`);

--
-- Contraintes pour la table `jour_ferie_politiques`
--
ALTER TABLE `jour_ferie_politiques`
  ADD CONSTRAINT `FKanv6394c7na3oqdm38nebatgf` FOREIGN KEY (`jour_ferie_id_jour`) REFERENCES `jour_ferie` (`id_jour`),
  ADD CONSTRAINT `FKdiftf3k88hrfm8houoqb0bfnx` FOREIGN KEY (`politiques_id_politique`) REFERENCES `politique` (`id_politique`);

--
-- Contraintes pour la table `politique_type_conge`
--
ALTER TABLE `politique_type_conge`
  ADD CONSTRAINT `FK34qpdmpg1qbix52dwldqwxgwa` FOREIGN KEY (`type_conge_id_type_conge`) REFERENCES `type_conge` (`id_type_conge`),
  ADD CONSTRAINT `FKc1boxmqx8ry77n6pgad1gmw8i` FOREIGN KEY (`politique_id_politique`) REFERENCES `politique` (`id_politique`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
