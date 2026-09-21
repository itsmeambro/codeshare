package com.gym.management.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * All JPA entities for the member module, grouped into one file as nested
 * static classes. Each is still a standalone entity mapped to its own
 * table — nesting only affects where the .java source lives, not the
 * database schema or how Hibernate treats them.
 *
 * Referenced from other packages as GymMemberEntities.Member,
 * GymMemberEntities.EmergencyContact, etc., or via a direct nested import,
 * e.g. `import com.gym.management.member.entity.GymMemberEntities.Member;`.
 *
 * There are no JPA object relationships between them (see design note in
 * chat) — every cross-entity reference is a plain UUID foreign key column.
 */
public final class GymMemberEntities {

    private GymMemberEntities() {
    }

    // ------------------------------------------------------------------
    // Member
    // ------------------------------------------------------------------

    @Entity
    @Table(
            name = "members",
            uniqueConstraints = {
                    @UniqueConstraint(name = "uk_member_code", columnNames = "member_code"),
                    @UniqueConstraint(name = "uk_member_email", columnNames = "email")
            },
            indexes = @Index(name = "idx_member_branch_id", columnList = "branch_id")
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Member {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", updatable = false, nullable = false)
        private UUID id;

        @Column(name = "member_code", nullable = false, length = 32)
        private String memberCode;

        @Column(name = "branch_id", nullable = false)
        private UUID branchId;

        @NotBlank
        @Column(name = "first_name", nullable = false, length = 100)
        private String firstName;

        @Column(name = "last_name", length = 100)
        private String lastName;

        @Email
        @Column(name = "email", length = 150)
        private String email;

        @Column(name = "phone", length = 30)
        private String phone;

        @Column(name = "date_of_birth")
        private LocalDate dateOfBirth;

        @Enumerated(EnumType.STRING)
        @Column(name = "gender", length = 20)
        private Gender gender;

        @Column(name = "address_line", length = 255)
        private String addressLine;

        @Column(name = "city", length = 100)
        private String city;

        @Column(name = "membership_plan_id")
        private UUID membershipPlanId;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", nullable = false, length = 20)
        private MemberStatus status;

        @Column(name = "join_date")
        private LocalDate joinDate;

        @Column(name = "profile_photo_url", length = 500)
        private String profilePhotoUrl;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        public enum Gender {
            MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY
        }

        public enum MemberStatus {
            ACTIVE, INACTIVE, FROZEN, EXPIRED, CANCELLED
        }
    }

    // ------------------------------------------------------------------
    // EmergencyContact
    // ------------------------------------------------------------------

    @Entity
    @Table(
            name = "emergency_contacts",
            indexes = @Index(name = "idx_emergency_contact_member_id", columnList = "member_id")
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EmergencyContact {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", updatable = false, nullable = false)
        private UUID id;

        @Column(name = "member_id", nullable = false)
        private UUID memberId;

        @NotBlank
        @Column(name = "full_name", nullable = false, length = 150)
        private String fullName;

        @Enumerated(EnumType.STRING)
        @Column(name = "relationship", length = 30)
        private Relationship relationship;

        @NotBlank
        @Column(name = "primary_phone", nullable = false, length = 30)
        private String primaryPhone;

        @Column(name = "alternate_phone", length = 30)
        private String alternatePhone;

        @Email
        @Column(name = "email", length = 150)
        private String email;

        @Column(name = "address_line", length = 255)
        private String addressLine;

        @Column(name = "is_primary", nullable = false)
        @Builder.Default
        private boolean primary = false;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        public enum Relationship {
            SPOUSE, PARENT, SIBLING, CHILD, FRIEND, GUARDIAN, OTHER
        }
    }

    // ------------------------------------------------------------------
    // MedicalInformation
    // ------------------------------------------------------------------

    @Entity
    @Table(
            name = "medical_information",
            uniqueConstraints = @UniqueConstraint(name = "uk_medical_info_member_id", columnNames = "member_id")
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MedicalInformation {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", updatable = false, nullable = false)
        private UUID id;

        @Column(name = "member_id", nullable = false)
        private UUID memberId;

        @Enumerated(EnumType.STRING)
        @Column(name = "blood_group", length = 10)
        private BloodGroup bloodGroup;

        @Column(name = "allergies", columnDefinition = "TEXT")
        private String allergies;

        @Column(name = "chronic_conditions", columnDefinition = "TEXT")
        private String chronicConditions;

        @Column(name = "current_medications", columnDefinition = "TEXT")
        private String currentMedications;

        @Column(name = "physician_name", length = 150)
        private String physicianName;

        @Column(name = "physician_phone", length = 30)
        private String physicianPhone;

        @Column(name = "insurance_provider", length = 150)
        private String insuranceProvider;

        @Column(name = "insurance_policy_number", length = 100)
        private String insurancePolicyNumber;

        @Enumerated(EnumType.STRING)
        @Column(name = "fitness_clearance", length = 30)
        private FitnessClearance fitnessClearance;

        @Column(name = "consent_to_share_with_trainers", nullable = false)
        @Builder.Default
        private boolean consentToShareWithTrainers = false;

        @Column(name = "additional_notes", columnDefinition = "TEXT")
        private String additionalNotes;

        @UpdateTimestamp
        @Column(name = "last_updated_at")
        private LocalDateTime lastUpdatedAt;

        public enum BloodGroup {
            A_POS, A_NEG, B_POS, B_NEG, AB_POS, AB_NEG, O_POS, O_NEG, UNKNOWN
        }

        public enum FitnessClearance {
            CLEARED, CLEARED_WITH_RESTRICTIONS, NOT_CLEARED, PENDING_REVIEW
        }
    }

    // ------------------------------------------------------------------
    // MemberDocument
    // ------------------------------------------------------------------

    @Entity
    @Table(
            name = "member_documents",
            indexes = @Index(name = "idx_member_document_member_id", columnList = "member_id")
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MemberDocument {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", updatable = false, nullable = false)
        private UUID id;

        @Column(name = "member_id", nullable = false)
        private UUID memberId;

        @Enumerated(EnumType.STRING)
        @Column(name = "document_type", nullable = false, length = 30)
        private DocumentType documentType;

        @Column(name = "file_name", nullable = false, length = 255)
        private String fileName;

        @Column(name = "storage_path", nullable = false, length = 500)
        private String storagePath;

        @Column(name = "content_type", length = 100)
        private String contentType;

        @Column(name = "file_size_bytes")
        private Long fileSizeBytes;

        @Column(name = "expiry_date")
        private LocalDate expiryDate;

        @Column(name = "verified", nullable = false)
        @Builder.Default
        private boolean verified = false;

        @Column(name = "verified_by_staff_id")
        private UUID verifiedByStaffId;

        @Column(name = "verified_at")
        private LocalDateTime verifiedAt;

        @Column(name = "uploaded_by_staff_id")
        private UUID uploadedByStaffId;

        @CreationTimestamp
        @Column(name = "uploaded_at", nullable = false, updatable = false)
        private LocalDateTime uploadedAt;

        public enum DocumentType {
            GOVERNMENT_ID,
            MEMBERSHIP_CONTRACT,
            LIABILITY_WAIVER,
            MEDICAL_CERTIFICATE,
            PROFILE_PHOTO,
            PAR_Q_FORM,
            OTHER
        }
    }

    // ------------------------------------------------------------------
    // MemberNote
    // ------------------------------------------------------------------

    @Entity
    @Table(
            name = "member_notes",
            indexes = @Index(name = "idx_member_note_member_id", columnList = "member_id")
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MemberNote {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", updatable = false, nullable = false)
        private UUID id;

        @Column(name = "member_id", nullable = false)
        private UUID memberId;

        @Enumerated(EnumType.STRING)
        @Column(name = "note_type", nullable = false, length = 30)
        private NoteType noteType;

        @NotBlank
        @Column(name = "content", nullable = false, columnDefinition = "TEXT")
        private String content;

        @Column(name = "author_staff_id", nullable = false)
        private UUID authorStaffId;

        @Column(name = "author_name", length = 150)
        private String authorName;

        @Column(name = "staff_only", nullable = false)
        @Builder.Default
        private boolean staffOnly = true;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        public enum NoteType {
            GENERAL, DISCIPLINARY, ACHIEVEMENT, COMPLAINT, GOAL, FOLLOW_UP
        }
    }

    // ------------------------------------------------------------------
    // TrainerAssignment
    // ------------------------------------------------------------------

    @Entity
    @Table(
            name = "trainer_assignments",
            indexes = {
                    @Index(name = "idx_trainer_assignment_member_id", columnList = "member_id"),
                    @Index(name = "idx_trainer_assignment_trainer_id", columnList = "trainer_id")
            }
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TrainerAssignment {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", updatable = false, nullable = false)
        private UUID id;

        @Column(name = "member_id", nullable = false)
        private UUID memberId;

        @Column(name = "trainer_id", nullable = false)
        private UUID trainerId;

        @Enumerated(EnumType.STRING)
        @Column(name = "assignment_type", nullable = false, length = 30)
        private AssignmentType assignmentType;

        @Column(name = "start_date", nullable = false)
        private LocalDate startDate;

        @Column(name = "end_date")
        private LocalDate endDate;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", nullable = false, length = 20)
        @Builder.Default
        private AssignmentStatus status = AssignmentStatus.ACTIVE;

        @Column(name = "sessions_per_week")
        private Integer sessionsPerWeek;

        @Column(name = "goals", columnDefinition = "TEXT")
        private String goals;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        public enum AssignmentType {
            PERSONAL_TRAINING, GROUP_CLASS, REHABILITATION, NUTRITION_COACHING
        }

        public enum AssignmentStatus {
            ACTIVE, COMPLETED, CANCELLED, PAUSED
        }
    }
}
