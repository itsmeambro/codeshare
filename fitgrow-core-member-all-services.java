
package com.gym.management.member.service;

import com.gym.management.member.dto.GymMemberDtos.EmergencyContactDto;
import com.gym.management.member.dto.GymMemberDtos.MedicalInformationDto;
import com.gym.management.member.dto.GymMemberDtos.MemberDocumentDto;
import com.gym.management.member.dto.GymMemberDtos.MemberDto;
import com.gym.management.member.dto.GymMemberDtos.MemberNoteDto;
import com.gym.management.member.dto.GymMemberDtos.TrainerAssignmentDto;
import com.gym.management.member.entity.GymMemberEntities.EmergencyContact;
import com.gym.management.member.entity.GymMemberEntities.MedicalInformation;
import com.gym.management.member.entity.GymMemberEntities.Member;
import com.gym.management.member.entity.GymMemberEntities.MemberDocument;
import com.gym.management.member.entity.GymMemberEntities.MemberNote;
import com.gym.management.member.entity.GymMemberEntities.TrainerAssignment;
import com.gym.management.member.repository.GymMemberRepositories.EmergencyContactRepository;
import com.gym.management.member.repository.GymMemberRepositories.MedicalInformationRepository;
import com.gym.management.member.repository.GymMemberRepositories.MemberDocumentRepository;
import com.gym.management.member.repository.GymMemberRepositories.MemberNoteRepository;
import com.gym.management.member.repository.GymMemberRepositories.MemberRepository;
import com.gym.management.member.repository.GymMemberRepositories.TrainerAssignmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * All application services for the member module, grouped into one file as
 * nested static classes. Spring picks up @Service-annotated nested static
 * classes the same way as top-level ones — referenced elsewhere as
 * GymMemberServices.MemberService, etc., or via a direct nested import.
 */
public final class GymMemberServices {

    private GymMemberServices() {
    }

    // ------------------------------------------------------------------
    // MemberService
    // ------------------------------------------------------------------

    @Service
    @RequiredArgsConstructor
    @Transactional
    public static class MemberService {

        private final MemberRepository memberRepository;

        public MemberDto.Response create(MemberDto.Request request) {
            if (memberRepository.existsByMemberCode(request.memberCode())) {
                throw new IllegalArgumentException("Member code already in use: " + request.memberCode());
            }
            return toResponse(memberRepository.save(toEntity(request)));
        }

        @Transactional(readOnly = true)
        public MemberDto.Response getById(UUID id) {
            return toResponse(findEntityOrThrow(id));
        }

        @Transactional(readOnly = true)
        public List<MemberDto.Response> getAll() {
            return memberRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public List<MemberDto.Response> getByBranchId(UUID branchId) {
            return memberRepository.findByBranchId(branchId).stream().map(this::toResponse).collect(Collectors.toList());
        }

        public MemberDto.Response update(UUID id, MemberDto.Request request) {
            Member member = findEntityOrThrow(id);
            member.setMemberCode(request.memberCode());
            member.setBranchId(request.branchId());
            member.setFirstName(request.firstName());
            member.setLastName(request.lastName());
            member.setEmail(request.email());
            member.setPhone(request.phone());
            member.setDateOfBirth(request.dateOfBirth());
            member.setGender(request.gender());
            member.setAddressLine(request.addressLine());
            member.setCity(request.city());
            member.setMembershipPlanId(request.membershipPlanId());
            if (request.status() != null) {
                member.setStatus(request.status());
            }
            member.setJoinDate(request.joinDate());
            member.setProfilePhotoUrl(request.profilePhotoUrl());
            return toResponse(memberRepository.save(member));
        }

        public void delete(UUID id) {
            if (!memberRepository.existsById(id)) {
                throw new EntityNotFoundException("Member not found: " + id);
            }
            memberRepository.deleteById(id);
        }

        private Member findEntityOrThrow(UUID id) {
            return memberRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Member not found: " + id));
        }

        private Member toEntity(MemberDto.Request r) {
            return Member.builder()
                    .memberCode(r.memberCode())
                    .branchId(r.branchId())
                    .firstName(r.firstName())
                    .lastName(r.lastName())
                    .email(r.email())
                    .phone(r.phone())
                    .dateOfBirth(r.dateOfBirth())
                    .gender(r.gender())
                    .addressLine(r.addressLine())
                    .city(r.city())
                    .membershipPlanId(r.membershipPlanId())
                    .status(r.status() != null ? r.status() : Member.MemberStatus.ACTIVE)
                    .joinDate(r.joinDate())
                    .profilePhotoUrl(r.profilePhotoUrl())
                    .build();
        }

        private MemberDto.Response toResponse(Member m) {
            return new MemberDto.Response(
                    m.getId(), m.getMemberCode(), m.getBranchId(), m.getFirstName(), m.getLastName(),
                    m.getEmail(), m.getPhone(), m.getDateOfBirth(), m.getGender(), m.getAddressLine(),
                    m.getCity(), m.getMembershipPlanId(), m.getStatus(), m.getJoinDate(),
                    m.getProfilePhotoUrl(), m.getCreatedAt(), m.getUpdatedAt()
            );
        }
    }

    // ------------------------------------------------------------------
    // EmergencyContactService
    // ------------------------------------------------------------------

    @Service
    @RequiredArgsConstructor
    @Transactional
    public static class EmergencyContactService {

        private final EmergencyContactRepository emergencyContactRepository;

        public EmergencyContactDto.Response create(EmergencyContactDto.Request request) {
            return toResponse(emergencyContactRepository.save(toEntity(request)));
        }

        @Transactional(readOnly = true)
        public EmergencyContactDto.Response getById(UUID id) {
            return toResponse(findEntityOrThrow(id));
        }

        @Transactional(readOnly = true)
        public List<EmergencyContactDto.Response> getByMemberId(UUID memberId) {
            return emergencyContactRepository.findByMemberId(memberId).stream()
                    .map(this::toResponse).collect(Collectors.toList());
        }

        public EmergencyContactDto.Response update(UUID id, EmergencyContactDto.Request request) {
            EmergencyContact contact = findEntityOrThrow(id);
            contact.setMemberId(request.memberId());
            contact.setFullName(request.fullName());
            contact.setRelationship(request.relationship());
            contact.setPrimaryPhone(request.primaryPhone());
            contact.setAlternatePhone(request.alternatePhone());
            contact.setEmail(request.email());
            contact.setAddressLine(request.addressLine());
            contact.setPrimary(request.primary());
            return toResponse(emergencyContactRepository.save(contact));
        }

        public void delete(UUID id) {
            if (!emergencyContactRepository.existsById(id)) {
                throw new EntityNotFoundException("Emergency contact not found: " + id);
            }
            emergencyContactRepository.deleteById(id);
        }

        private EmergencyContact findEntityOrThrow(UUID id) {
            return emergencyContactRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Emergency contact not found: " + id));
        }

        private EmergencyContact toEntity(EmergencyContactDto.Request r) {
            return EmergencyContact.builder()
                    .memberId(r.memberId())
                    .fullName(r.fullName())
                    .relationship(r.relationship())
                    .primaryPhone(r.primaryPhone())
                    .alternatePhone(r.alternatePhone())
                    .email(r.email())
                    .addressLine(r.addressLine())
                    .primary(r.primary())
                    .build();
        }

        private EmergencyContactDto.Response toResponse(EmergencyContact c) {
            return new EmergencyContactDto.Response(
                    c.getId(), c.getMemberId(), c.getFullName(), c.getRelationship(),
                    c.getPrimaryPhone(), c.getAlternatePhone(), c.getEmail(), c.getAddressLine(),
                    c.isPrimary(), c.getCreatedAt()
            );
        }
    }

    // ------------------------------------------------------------------
    // MedicalInformationService
    // ------------------------------------------------------------------

    @Service
    @RequiredArgsConstructor
    @Transactional
    public static class MedicalInformationService {

        private final MedicalInformationRepository medicalInformationRepository;

        public MedicalInformationDto.Response create(MedicalInformationDto.Request request) {
            if (medicalInformationRepository.existsByMemberId(request.memberId())) {
                throw new IllegalArgumentException(
                        "Medical information already exists for member: " + request.memberId()
                                + ". Use update instead.");
            }
            return toResponse(medicalInformationRepository.save(toEntity(request)));
        }

        @Transactional(readOnly = true)
        public MedicalInformationDto.Response getById(UUID id) {
            return toResponse(findEntityOrThrow(id));
        }

        @Transactional(readOnly = true)
        public MedicalInformationDto.Response getByMemberId(UUID memberId) {
            return medicalInformationRepository.findByMemberId(memberId)
                    .map(this::toResponse)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "No medical information on file for member: " + memberId));
        }

        public MedicalInformationDto.Response update(UUID id, MedicalInformationDto.Request request) {
            MedicalInformation info = findEntityOrThrow(id);
            info.setBloodGroup(request.bloodGroup());
            info.setAllergies(request.allergies());
            info.setChronicConditions(request.chronicConditions());
            info.setCurrentMedications(request.currentMedications());
            info.setPhysicianName(request.physicianName());
            info.setPhysicianPhone(request.physicianPhone());
            info.setInsuranceProvider(request.insuranceProvider());
            info.setInsurancePolicyNumber(request.insurancePolicyNumber());
            info.setFitnessClearance(request.fitnessClearance());
            info.setConsentToShareWithTrainers(request.consentToShareWithTrainers());
            info.setAdditionalNotes(request.additionalNotes());
            return toResponse(medicalInformationRepository.save(info));
        }

        public void delete(UUID id) {
            if (!medicalInformationRepository.existsById(id)) {
                throw new EntityNotFoundException("Medical information not found: " + id);
            }
            medicalInformationRepository.deleteById(id);
        }

        private MedicalInformation findEntityOrThrow(UUID id) {
            return medicalInformationRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Medical information not found: " + id));
        }

        private MedicalInformation toEntity(MedicalInformationDto.Request r) {
            return MedicalInformation.builder()
                    .memberId(r.memberId())
                    .bloodGroup(r.bloodGroup())
                    .allergies(r.allergies())
                    .chronicConditions(r.chronicConditions())
                    .currentMedications(r.currentMedications())
                    .physicianName(r.physicianName())
                    .physicianPhone(r.physicianPhone())
                    .insuranceProvider(r.insuranceProvider())
                    .insurancePolicyNumber(r.insurancePolicyNumber())
                    .fitnessClearance(r.fitnessClearance())
                    .consentToShareWithTrainers(r.consentToShareWithTrainers())
                    .additionalNotes(r.additionalNotes())
                    .build();
        }

        private MedicalInformationDto.Response toResponse(MedicalInformation m) {
            return new MedicalInformationDto.Response(
                    m.getId(), m.getMemberId(), m.getBloodGroup(), m.getAllergies(),
                    m.getChronicConditions(), m.getCurrentMedications(), m.getPhysicianName(),
                    m.getPhysicianPhone(), m.getInsuranceProvider(), m.getInsurancePolicyNumber(),
                    m.getFitnessClearance(), m.isConsentToShareWithTrainers(), m.getAdditionalNotes(),
                    m.getLastUpdatedAt()
            );
        }
    }

    // ------------------------------------------------------------------
    // MemberDocumentService
    // ------------------------------------------------------------------

    @Service
    @RequiredArgsConstructor
    @Transactional
    public static class MemberDocumentService {

        private final MemberDocumentRepository memberDocumentRepository;

        public MemberDocumentDto.Response create(MemberDocumentDto.Request request) {
            return toResponse(memberDocumentRepository.save(toEntity(request)));
        }

        @Transactional(readOnly = true)
        public MemberDocumentDto.Response getById(UUID id) {
            return toResponse(findEntityOrThrow(id));
        }

        @Transactional(readOnly = true)
        public List<MemberDocumentDto.Response> getByMemberId(UUID memberId) {
            return memberDocumentRepository.findByMemberId(memberId).stream()
                    .map(this::toResponse).collect(Collectors.toList());
        }

        public MemberDocumentDto.Response update(UUID id, MemberDocumentDto.Request request) {
            MemberDocument document = findEntityOrThrow(id);
            document.setMemberId(request.memberId());
            document.setDocumentType(request.documentType());
            document.setFileName(request.fileName());
            document.setStoragePath(request.storagePath());
            document.setContentType(request.contentType());
            document.setFileSizeBytes(request.fileSizeBytes());
            document.setExpiryDate(request.expiryDate());
            document.setUploadedByStaffId(request.uploadedByStaffId());
            return toResponse(memberDocumentRepository.save(document));
        }

        public MemberDocumentDto.Response verify(UUID id, MemberDocumentDto.VerifyRequest request) {
            MemberDocument document = findEntityOrThrow(id);
            document.setVerified(true);
            document.setVerifiedByStaffId(request.verifiedByStaffId());
            document.setVerifiedAt(LocalDateTime.now());
            return toResponse(memberDocumentRepository.save(document));
        }

        public void delete(UUID id) {
            if (!memberDocumentRepository.existsById(id)) {
                throw new EntityNotFoundException("Member document not found: " + id);
            }
            memberDocumentRepository.deleteById(id);
        }

        private MemberDocument findEntityOrThrow(UUID id) {
            return memberDocumentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Member document not found: " + id));
        }

        private MemberDocument toEntity(MemberDocumentDto.Request r) {
            return MemberDocument.builder()
                    .memberId(r.memberId())
                    .documentType(r.documentType())
                    .fileName(r.fileName())
                    .storagePath(r.storagePath())
                    .contentType(r.contentType())
                    .fileSizeBytes(r.fileSizeBytes())
                    .expiryDate(r.expiryDate())
                    .uploadedByStaffId(r.uploadedByStaffId())
                    .verified(false)
                    .build();
        }

        private MemberDocumentDto.Response toResponse(MemberDocument d) {
            return new MemberDocumentDto.Response(
                    d.getId(), d.getMemberId(), d.getDocumentType(), d.getFileName(), d.getStoragePath(),
                    d.getContentType(), d.getFileSizeBytes(), d.getExpiryDate(), d.isVerified(),
                    d.getVerifiedByStaffId(), d.getVerifiedAt(), d.getUploadedByStaffId(), d.getUploadedAt()
            );
        }
    }

    // ------------------------------------------------------------------
    // MemberNoteService
    // ------------------------------------------------------------------

    @Service
    @RequiredArgsConstructor
    @Transactional
    public static class MemberNoteService {

        private final MemberNoteRepository memberNoteRepository;

        public MemberNoteDto.Response create(MemberNoteDto.Request request) {
            return toResponse(memberNoteRepository.save(toEntity(request)));
        }

        @Transactional(readOnly = true)
        public MemberNoteDto.Response getById(UUID id) {
            return toResponse(findEntityOrThrow(id));
        }

        @Transactional(readOnly = true)
        public List<MemberNoteDto.Response> getByMemberId(UUID memberId) {
            return memberNoteRepository.findByMemberId(memberId).stream()
                    .map(this::toResponse).collect(Collectors.toList());
        }

        public MemberNoteDto.Response update(UUID id, MemberNoteDto.Request request) {
            MemberNote note = findEntityOrThrow(id);
            note.setMemberId(request.memberId());
            note.setNoteType(request.noteType());
            note.setContent(request.content());
            note.setAuthorStaffId(request.authorStaffId());
            note.setAuthorName(request.authorName());
            note.setStaffOnly(request.staffOnly());
            return toResponse(memberNoteRepository.save(note));
        }

        public void delete(UUID id) {
            if (!memberNoteRepository.existsById(id)) {
                throw new EntityNotFoundException("Member note not found: " + id);
            }
            memberNoteRepository.deleteById(id);
        }

        private MemberNote findEntityOrThrow(UUID id) {
            return memberNoteRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Member note not found: " + id));
        }

        private MemberNote toEntity(MemberNoteDto.Request r) {
            return MemberNote.builder()
                    .memberId(r.memberId())
                    .noteType(r.noteType())
                    .content(r.content())
                    .authorStaffId(r.authorStaffId())
                    .authorName(r.authorName())
                    .staffOnly(r.staffOnly())
                    .build();
        }

        private MemberNoteDto.Response toResponse(MemberNote n) {
            return new MemberNoteDto.Response(
                    n.getId(), n.getMemberId(), n.getNoteType(), n.getContent(), n.getAuthorStaffId(),
                    n.getAuthorName(), n.isStaffOnly(), n.getCreatedAt(), n.getUpdatedAt()
            );
        }
    }

    // ------------------------------------------------------------------
    // TrainerAssignmentService
    // ------------------------------------------------------------------

    @Service
    @RequiredArgsConstructor
    @Transactional
    public static class TrainerAssignmentService {

        private final TrainerAssignmentRepository trainerAssignmentRepository;

        public TrainerAssignmentDto.Response create(TrainerAssignmentDto.Request request) {
            return toResponse(trainerAssignmentRepository.save(toEntity(request)));
        }

        @Transactional(readOnly = true)
        public TrainerAssignmentDto.Response getById(UUID id) {
            return toResponse(findEntityOrThrow(id));
        }

        @Transactional(readOnly = true)
        public List<TrainerAssignmentDto.Response> getByMemberId(UUID memberId) {
            return trainerAssignmentRepository.findByMemberId(memberId).stream()
                    .map(this::toResponse).collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public List<TrainerAssignmentDto.Response> getByTrainerId(UUID trainerId) {
            return trainerAssignmentRepository.findByTrainerId(trainerId).stream()
                    .map(this::toResponse).collect(Collectors.toList());
        }

        public TrainerAssignmentDto.Response update(UUID id, TrainerAssignmentDto.Request request) {
            TrainerAssignment assignment = findEntityOrThrow(id);
            assignment.setMemberId(request.memberId());
            assignment.setTrainerId(request.trainerId());
            assignment.setAssignmentType(request.assignmentType());
            assignment.setStartDate(request.startDate());
            assignment.setEndDate(request.endDate());
            if (request.status() != null) {
                assignment.setStatus(request.status());
            }
            assignment.setSessionsPerWeek(request.sessionsPerWeek());
            assignment.setGoals(request.goals());
            return toResponse(trainerAssignmentRepository.save(assignment));
        }

        public void delete(UUID id) {
            if (!trainerAssignmentRepository.existsById(id)) {
                throw new EntityNotFoundException("Trainer assignment not found: " + id);
            }
            trainerAssignmentRepository.deleteById(id);
        }

        private TrainerAssignment findEntityOrThrow(UUID id) {
            return trainerAssignmentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Trainer assignment not found: " + id));
        }

        private TrainerAssignment toEntity(TrainerAssignmentDto.Request r) {
            return TrainerAssignment.builder()
                    .memberId(r.memberId())
                    .trainerId(r.trainerId())
                    .assignmentType(r.assignmentType())
                    .startDate(r.startDate())
                    .endDate(r.endDate())
                    .status(r.status() != null ? r.status() : TrainerAssignment.AssignmentStatus.ACTIVE)
                    .sessionsPerWeek(r.sessionsPerWeek())
                    .goals(r.goals())
                    .build();
        }

        private TrainerAssignmentDto.Response toResponse(TrainerAssignment a) {
            return new TrainerAssignmentDto.Response(
                    a.getId(), a.getMemberId(), a.getTrainerId(), a.getAssignmentType(), a.getStartDate(),
                    a.getEndDate(), a.getStatus(), a.getSessionsPerWeek(), a.getGoals(), a.getCreatedAt()
            );
        }
    }
}
