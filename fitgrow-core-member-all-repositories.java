package com.gym.management.member.repository;

import com.gym.management.member.entity.GymMemberEntities.EmergencyContact;
import com.gym.management.member.entity.GymMemberEntities.MedicalInformation;
import com.gym.management.member.entity.GymMemberEntities.Member;
import com.gym.management.member.entity.GymMemberEntities.MemberDocument;
import com.gym.management.member.entity.GymMemberEntities.MemberNote;
import com.gym.management.member.entity.GymMemberEntities.TrainerAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * All Spring Data JPA repositories for the member module, grouped into one
 * file as nested interfaces. Spring's classpath scanning picks up nested
 * interfaces the same as top-level ones, so no extra configuration is
 * needed — referenced elsewhere as GymMemberRepositories.MemberRepository,
 * etc., or via a direct nested import.
 */
public final class GymMemberRepositories {

    private GymMemberRepositories() {
    }

    public interface MemberRepository extends JpaRepository<Member, UUID> {

        Optional<Member> findByMemberCode(String memberCode);

        Optional<Member> findByEmail(String email);

        List<Member> findByBranchId(UUID branchId);

        List<Member> findByStatus(Member.MemberStatus status);

        boolean existsByMemberCode(String memberCode);
    }

    public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, UUID> {

        List<EmergencyContact> findByMemberId(UUID memberId);
    }

    public interface MedicalInformationRepository extends JpaRepository<MedicalInformation, UUID> {

        Optional<MedicalInformation> findByMemberId(UUID memberId);

        boolean existsByMemberId(UUID memberId);
    }

    public interface MemberDocumentRepository extends JpaRepository<MemberDocument, UUID> {

        List<MemberDocument> findByMemberId(UUID memberId);

        List<MemberDocument> findByMemberIdAndDocumentType(UUID memberId, MemberDocument.DocumentType documentType);
    }

    public interface MemberNoteRepository extends JpaRepository<MemberNote, UUID> {

        List<MemberNote> findByMemberId(UUID memberId);

        List<MemberNote> findByMemberIdAndStaffOnly(UUID memberId, boolean staffOnly);
    }

    public interface TrainerAssignmentRepository extends JpaRepository<TrainerAssignment, UUID> {

        List<TrainerAssignment> findByMemberId(UUID memberId);

        List<TrainerAssignment> findByTrainerId(UUID trainerId);

        List<TrainerAssignment> findByMemberIdAndStatus(UUID memberId, TrainerAssignment.AssignmentStatus status);
    }
}
