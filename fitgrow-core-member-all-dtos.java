
package com.gym.management.member.dto;

import com.gym.management.member.entity.GymMemberEntities.EmergencyContact;
import com.gym.management.member.entity.GymMemberEntities.MedicalInformation;
import com.gym.management.member.entity.GymMemberEntities.Member;
import com.gym.management.member.entity.GymMemberEntities.MemberDocument;
import com.gym.management.member.entity.GymMemberEntities.MemberNote;
import com.gym.management.member.entity.GymMemberEntities.TrainerAssignment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * All request/response DTOs for the member module, grouped into one file
 * as nested static classes, each holding its own Request/Response records.
 * Referenced elsewhere as GymMemberDtos.MemberDto.Request, etc., or via a
 * direct nested import, e.g.
 * `import com.gym.management.member.dto.GymMemberDtos.MemberDto;`
 * after which MemberDto.Request / MemberDto.Response work as before.
 */
public final class GymMemberDtos {

    private GymMemberDtos() {
    }

    // ------------------------------------------------------------------
    // MemberDto
    // ------------------------------------------------------------------

    public static final class MemberDto {

        private MemberDto() {
        }

        public record Request(
                @NotBlank(message = "memberCode is required") String memberCode,
                @NotNull(message = "branchId is required") UUID branchId,
                @NotBlank(message = "firstName is required") String firstName,
                String lastName,
                @Email(message = "email must be valid") String email,
                String phone,
                LocalDate dateOfBirth,
                Member.Gender gender,
                String addressLine,
                String city,
                UUID membershipPlanId,
                Member.MemberStatus status,
                LocalDate joinDate,
                String profilePhotoUrl
        ) {
        }

        public record Response(
                UUID id,
                String memberCode,
                UUID branchId,
                String firstName,
                String lastName,
                String email,
                String phone,
                LocalDate dateOfBirth,
                Member.Gender gender,
                String addressLine,
                String city,
                UUID membershipPlanId,
                Member.MemberStatus status,
                LocalDate joinDate,
                String profilePhotoUrl,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {
        }
    }

    // ------------------------------------------------------------------
    // EmergencyContactDto
    // ------------------------------------------------------------------

    public static final class EmergencyContactDto {

        private EmergencyContactDto() {
        }

        public record Request(
                @NotNull(message = "memberId is required") UUID memberId,
                @NotBlank(message = "fullName is required") String fullName,
                EmergencyContact.Relationship relationship,
                @NotBlank(message = "primaryPhone is required") String primaryPhone,
                String alternatePhone,
                @Email String email,
                String addressLine,
                boolean primary
        ) {
        }

        public record Response(
                UUID id,
                UUID memberId,
                String fullName,
                EmergencyContact.Relationship relationship,
                String primaryPhone,
                String alternatePhone,
                String email,
                String addressLine,
                boolean primary,
                LocalDateTime createdAt
        ) {
        }
    }

    // ------------------------------------------------------------------
    // MedicalInformationDto
    // ------------------------------------------------------------------

    public static final class MedicalInformationDto {

        private MedicalInformationDto() {
        }

        public record Request(
                @NotNull(message = "memberId is required") UUID memberId,
                MedicalInformation.BloodGroup bloodGroup,
                String allergies,
                String chronicConditions,
                String currentMedications,
                String physicianName,
                String physicianPhone,
                String insuranceProvider,
                String insurancePolicyNumber,
                MedicalInformation.FitnessClearance fitnessClearance,
                boolean consentToShareWithTrainers,
                String additionalNotes
        ) {
        }

        public record Response(
                UUID id,
                UUID memberId,
                MedicalInformation.BloodGroup bloodGroup,
                String allergies,
                String chronicConditions,
                String currentMedications,
                String physicianName,
                String physicianPhone,
                String insuranceProvider,
                String insurancePolicyNumber,
                MedicalInformation.FitnessClearance fitnessClearance,
                boolean consentToShareWithTrainers,
                String additionalNotes,
                LocalDateTime lastUpdatedAt
        ) {
        }
    }

    // ------------------------------------------------------------------
    // MemberDocumentDto
    // ------------------------------------------------------------------

    public static final class MemberDocumentDto {

        private MemberDocumentDto() {
        }

        public record Request(
                @NotNull(message = "memberId is required") UUID memberId,
                @NotNull(message = "documentType is required") MemberDocument.DocumentType documentType,
                @NotBlank(message = "fileName is required") String fileName,
                @NotBlank(message = "storagePath is required") String storagePath,
                String contentType,
                Long fileSizeBytes,
                LocalDate expiryDate,
                UUID uploadedByStaffId
        ) {
        }

        public record Response(
                UUID id,
                UUID memberId,
                MemberDocument.DocumentType documentType,
                String fileName,
                String storagePath,
                String contentType,
                Long fileSizeBytes,
                LocalDate expiryDate,
                boolean verified,
                UUID verifiedByStaffId,
                LocalDateTime verifiedAt,
                UUID uploadedByStaffId,
                LocalDateTime uploadedAt
        ) {
        }

        /** Payload for the dedicated verify action, kept separate from the general update. */
        public record VerifyRequest(
                @NotNull(message = "verifiedByStaffId is required") UUID verifiedByStaffId
        ) {
        }
    }

    // ------------------------------------------------------------------
    // MemberNoteDto
    // ------------------------------------------------------------------

    public static final class MemberNoteDto {

        private MemberNoteDto() {
        }

        public record Request(
                @NotNull(message = "memberId is required") UUID memberId,
                @NotNull(message = "noteType is required") MemberNote.NoteType noteType,
                @NotBlank(message = "content is required") String content,
                @NotNull(message = "authorStaffId is required") UUID authorStaffId,
                String authorName,
                boolean staffOnly
        ) {
        }

        public record Response(
                UUID id,
                UUID memberId,
                MemberNote.NoteType noteType,
                String content,
                UUID authorStaffId,
                String authorName,
                boolean staffOnly,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {
        }
    }

    // ------------------------------------------------------------------
    // TrainerAssignmentDto
    // ------------------------------------------------------------------

    public static final class TrainerAssignmentDto {

        private TrainerAssignmentDto() {
        }

        public record Request(
                @NotNull(message = "memberId is required") UUID memberId,
                @NotNull(message = "trainerId is required") UUID trainerId,
                @NotNull(message = "assignmentType is required") TrainerAssignment.AssignmentType assignmentType,
                @NotNull(message = "startDate is required") LocalDate startDate,
                LocalDate endDate,
                TrainerAssignment.AssignmentStatus status,
                Integer sessionsPerWeek,
                String goals
        ) {
        }

        public record Response(
                UUID id,
                UUID memberId,
                UUID trainerId,
                TrainerAssignment.AssignmentType assignmentType,
                LocalDate startDate,
                LocalDate endDate,
                TrainerAssignment.AssignmentStatus status,
                Integer sessionsPerWeek,
                String goals,
                LocalDateTime createdAt
        ) {
        }
    }
}
