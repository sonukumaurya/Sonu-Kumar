package com.example.data

object SampleData {

  val chapters: List<Chapter> = listOf(
    // Physics - Class 11
    Chapter(
      id = "p1",
      subject = SubjectType.PHYSICS,
      name = "Kinematics & Projectile Motion",
      classLevel = 11,
      weightage = WeightageLevel.MEDIUM,
      pyqsSolved = 45,
      totalPyqs = 50,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 14,
      keyConcepts = listOf("1D/2D Relative Motion", "Trajectories", "Radius of Curvature"),
      topics = listOf(
        SyllabusTopic("p1_t1", "1D Motion & Calculus Graphs (v-t, a-t, x-t)", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "v = dx/dt, a = v dv/dx"),
        SyllabusTopic("p1_t2", "2D Projectile Motion on Ground & Inclined Planes", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "T = 2u sinθ/g, R = u² sin2θ/g"),
        SyllabusTopic("p1_t3", "Relative Velocity in 1D & 2D (Rain-Man, River-Boat)", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "v_AB = v_A - v_B"),
        SyllabusTopic("p1_t4", "Radius of Curvature & Tangential/Normal Acceleration", isCompleted = true, isHighYield = false, tag = "Concept", keyFormulaHint = "R = v² / a_normal")
      )
    ),
    Chapter(
      id = "p2",
      subject = SubjectType.PHYSICS,
      name = "Laws of Motion & Friction",
      classLevel = 11,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 38,
      totalPyqs = 45,
      status = ChapterStatus.MASTERED,
      revisionCount = 2,
      keyFormulaCount = 12,
      keyConcepts = listOf("Pseudo Force", "Friction on Inclined Plane", "Pulley Constraints"),
      topics = listOf(
        SyllabusTopic("p2_t1", "Free Body Diagrams (FBD) & Equilibrium Equations", isCompleted = true, isHighYield = false, tag = "Foundational", keyFormulaHint = "ΣF = 0 or ΣF = m a"),
        SyllabusTopic("p2_t2", "String, Pulley & Wedge Constraint Relations", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Σ (T · a) = 0"),
        SyllabusTopic("p2_t3", "Static, Kinetic Friction & Angle of Repose", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "f_s ≤ μ_s N, f_k = μ_k N"),
        SyllabusTopic("p2_t4", "Pseudo Force in Non-Inertial Accelerated Frames", isCompleted = true, isHighYield = true, tag = "Concept", keyFormulaHint = "F_pseudo = -m a_frame"),
        SyllabusTopic("p2_t5", "Two-Block Friction Systems & Relative Slipping", isCompleted = true, isHighYield = true, tag = "Advance Focus", keyFormulaHint = "Check max static friction first")
      )
    ),
    Chapter(
      id = "p3",
      subject = SubjectType.PHYSICS,
      name = "Work, Energy & Power",
      classLevel = 11,
      weightage = WeightageLevel.MEDIUM,
      pyqsSolved = 32,
      totalPyqs = 40,
      status = ChapterStatus.REVISED,
      revisionCount = 2,
      keyFormulaCount = 10,
      keyConcepts = listOf("Work-Energy Theorem", "Conservative Forces", "Vertical Circle Dynamics"),
      topics = listOf(
        SyllabusTopic("p3_t1", "Work by Constant & Variable Forces", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "W = ∫ F · dr"),
        SyllabusTopic("p3_t2", "Work-Energy Theorem & Conservation of Mechanical Energy", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "W_net = ΔK = K_f - K_i"),
        SyllabusTopic("p3_t3", "Potential Energy Curve & Stable/Unstable Equilibrium", isCompleted = true, isHighYield = false, tag = "Concept", keyFormulaHint = "F = -dU/dx, d²U/dx² > 0 (stable)"),
        SyllabusTopic("p3_t4", "Vertical Circular Motion & Looping Thresholds", isCompleted = false, isHighYield = true, tag = "High Yield", keyFormulaHint = "v_bottom ≥ √(5gR), v_top ≥ √(gR)"),
        SyllabusTopic("p3_t5", "Instantaneous & Average Power", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "P = F · v = dW/dt")
      )
    ),
    Chapter(
      id = "p4",
      subject = SubjectType.PHYSICS,
      name = "Rotational Dynamics (COM & MOI)",
      classLevel = 11,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 42,
      totalPyqs = 60,
      status = ChapterStatus.IN_PROGRESS,
      revisionCount = 1,
      keyFormulaCount = 18,
      keyConcepts = listOf("Moment of Inertia Theorems", "Torque = Iα", "Rolling without Slipping", "Angular Momentum Conservation"),
      topics = listOf(
        SyllabusTopic("p4_t1", "Center of Mass (COM) for Discrete & Continuous Bodies", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "R_cm = (∫ r dm) / M"),
        SyllabusTopic("p4_t2", "Moment of Inertia: Parallel & Perpendicular Axis Theorems", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "I = I_cm + M d²"),
        SyllabusTopic("p4_t3", "Torque, Fixed Axis Rotation & Angular Acceleration (τ = Iα)", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "τ_net = I α = dL/dt"),
        SyllabusTopic("p4_t4", "Rolling Motion Without Slipping on Flat & Inclined Planes", isCompleted = false, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "v_cm = ω R, a_cm = g sinθ / (1 + k²/R²)"),
        SyllabusTopic("p4_t5", "Conservation of Angular Momentum (L = Iω or L = r × p)", isCompleted = false, isHighYield = true, tag = "Advance Focus", keyFormulaHint = "If Στ_ext = 0, then L_initial = L_final"),
        SyllabusTopic("p4_t6", "Toppling vs Sliding Conditions for Rigid Blocks", isCompleted = false, isHighYield = false, tag = "Concept", keyFormulaHint = "Torque of normal force shifts to edge")
      )
    ),
    Chapter(
      id = "p5",
      subject = SubjectType.PHYSICS,
      name = "Thermodynamics & KTG",
      classLevel = 11,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 50,
      totalPyqs = 55,
      status = ChapterStatus.MASTERED,
      revisionCount = 4,
      keyFormulaCount = 16,
      keyConcepts = listOf("First Law dQ=dU+dW", "Carnot Engine & Efficiency", "Polytropic Processes PV^n=C", "Degrees of Freedom"),
      topics = listOf(
        SyllabusTopic("p5_t1", "Kinetic Theory of Gases (KTG) & Degrees of Freedom", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "P = (1/3) ρ v_rms², U = (f/2) n R T"),
        SyllabusTopic("p5_t2", "First Law of Thermodynamics (dQ = dU + dW)", isCompleted = true, isHighYield = true, tag = "Core", keyFormulaHint = "dU = n C_v dT, dW = ∫ P dV"),
        SyllabusTopic("p5_t3", "Thermodynamic Processes (Isochoric, Isobaric, Isothermal, Adiabatic)", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "PV^γ = const, W_adiabatic = (P1V1 - P2V2)/(γ - 1)"),
        SyllabusTopic("p5_t4", "Carnot Engine, Heat Pumps & Second Law Efficiency", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "η = 1 - T_c / T_h = W / Q_in"),
        SyllabusTopic("p5_t5", "Polytropic Processes PV^n = C & Molar Heat Capacity", isCompleted = true, isHighYield = false, tag = "Advance Focus", keyFormulaHint = "C = C_v + R / (1 - n)")
      )
    ),
    Chapter(
      id = "p6",
      subject = SubjectType.PHYSICS,
      name = "Fluid Mechanics & Surface Tension",
      classLevel = 11,
      weightage = WeightageLevel.MEDIUM,
      pyqsSolved = 22,
      totalPyqs = 40,
      status = ChapterStatus.IN_PROGRESS,
      revisionCount = 1,
      keyFormulaCount = 11,
      keyConcepts = listOf("Bernoulli's Equation", "Capillary Rise", "Terminal Velocity"),
      topics = listOf(
        SyllabusTopic("p6_t1", "Fluid Statics, Pascal's Principle & Archimedes Buoyancy", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "P = P0 + ρgh, F_b = ρ_fluid V_sub g"),
        SyllabusTopic("p6_t2", "Equation of Continuity & Bernoulli's Theorem", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "A1 v1 = A2 v2, P + 1/2 ρv² + ρgh = const"),
        SyllabusTopic("p6_t3", "Torricelli's Efflux Law & Siphon Dynamics", isCompleted = false, isHighYield = false, tag = "Concept", keyFormulaHint = "v = √(2gh)"),
        SyllabusTopic("p6_t4", "Viscosity, Poiseuille's Flow & Terminal Velocity", isCompleted = false, isHighYield = true, tag = "High Yield", keyFormulaHint = "v_t = 2r²(ρ - σ)g / (9η)"),
        SyllabusTopic("p6_t5", "Surface Tension, Excess Pressure & Capillary Rise", isCompleted = false, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "ΔP = 2T/R (drop), h = 2T cosθ / (r ρ g)")
      )
    ),

    // Physics - Class 12
    Chapter(
      id = "p7",
      subject = SubjectType.PHYSICS,
      name = "Electrostatics & Capacitance",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 48,
      totalPyqs = 55,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 20,
      keyConcepts = listOf("Gauss Law Flux", "Electric Potential", "Capacitor Dielectrics", "Energy Density"),
      topics = listOf(
        SyllabusTopic("p7_t1", "Coulomb's Law, Principle of Superposition & Continuous Charge", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "F = k q1 q2 / r²"),
        SyllabusTopic("p7_t2", "Gauss's Law, Electric Flux & Symmetric Field Distributions", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Φ = ∮ E · dA = q_enclosed / ε₀"),
        SyllabusTopic("p7_t3", "Electrostatic Potential, Dipole in Field & Self-Energy", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "V = k q / r, U_dipole = -p · E"),
        SyllabusTopic("p7_t4", "Capacitance, Series/Parallel & Stored Energy Density", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "C = ε₀ A / d, u = 1/2 ε₀ E²"),
        SyllabusTopic("p7_t5", "Dielectrics Insertion, Charge Sharing & Force on Plates", isCompleted = true, isHighYield = true, tag = "Advance Focus", keyFormulaHint = "C' = K C, Heat Loss = 1/2 (C1 C2)/(C1+C2) (V1-V2)²")
      )
    ),
    Chapter(
      id = "p8",
      subject = SubjectType.PHYSICS,
      name = "Current Electricity",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 40,
      totalPyqs = 45,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 15,
      keyConcepts = listOf("Kirchhoff's Laws", "Potentiometer & Meter Bridge", "RC Circuits Transient"),
      topics = listOf(
        SyllabusTopic("p8_t1", "Drift Velocity, Current Density, Ohm's Law & Resistivity", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "I = n e A v_d, v_d = e E τ / m"),
        SyllabusTopic("p8_t2", "Kirchhoff's Junction (KCL) & Loop (KVL) Circuit Solving", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Σ I_in = Σ I_out, Σ ΔV = 0"),
        SyllabusTopic("p8_t3", "Wheatstone Bridge, Meter Bridge & Potentiometer Sensitivity", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "R1/R2 = R3/R4, E1/E2 = l1/l2"),
        SyllabusTopic("p8_t4", "RC Circuit Charging & Discharging Transient Equations", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "q(t) = Q_max (1 - e^(-t/RC)), τ = RC"),
        SyllabusTopic("p8_t5", "Symmetry Rules (Mirror & Perpendicular) for Complex Resistor Grids", isCompleted = true, isHighYield = false, tag = "Tricks", keyFormulaHint = "Equipotential node folding")
      )
    ),
    Chapter(
      id = "p9",
      subject = SubjectType.PHYSICS,
      name = "Magnetism & Magnetic Effects of Current",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 35,
      totalPyqs = 45,
      status = ChapterStatus.REVISED,
      revisionCount = 2,
      keyFormulaCount = 16,
      keyConcepts = listOf("Biot-Savart Law", "Ampere's Circuital Law", "Torque on Dipole", "Cyclotron Motion"),
      topics = listOf(
        SyllabusTopic("p9_t1", "Biot-Savart Law for Straight Wires, Circular Loops & Solenoids", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "B_center = μ₀ I / (2R), B_axis = μ₀ I R² / (2(R²+x²)^1.5)"),
        SyllabusTopic("p9_t2", "Ampere's Circuital Law & Magnetic Fields of Cylinders/Sheets", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "∮ B · dl = μ₀ I_enclosed"),
        SyllabusTopic("p9_t3", "Lorentz Force, Helical Motion & Cyclotron Frequency", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "F = q (E + v × B), r = m v_perp / (q B)"),
        SyllabusTopic("p9_t4", "Magnetic Dipole Moment, Torque & Potential Energy of Loops", isCompleted = false, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "M = N I A, τ = M × B, U = -M · B"),
        SyllabusTopic("p9_t5", "Magnetic Materials (Dia, Para, Ferro) & Earth's Magnetism", isCompleted = true, isHighYield = false, tag = "NCERT Must", keyFormulaHint = "B_H = B cosδ, χ = M / H")
      )
    ),
    Chapter(
      id = "p10",
      subject = SubjectType.PHYSICS,
      name = "Electromagnetic Induction & AC",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 30,
      totalPyqs = 40,
      status = ChapterStatus.IN_PROGRESS,
      revisionCount = 1,
      keyFormulaCount = 14,
      keyConcepts = listOf("Faraday's & Lenz's Law", "Self & Mutual Inductance", "LCR Resonance & Q-Factor"),
      topics = listOf(
        SyllabusTopic("p10_t1", "Magnetic Flux, Faraday's & Lenz's Law of Induction", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "ε = -dΦ/dt, Φ = B · A"),
        SyllabusTopic("p10_t2", "Motional EMF (Translational & Rotational Rods in B-Field)", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "ε = B v l, ε_rot = 1/2 B ω l²"),
        SyllabusTopic("p10_t3", "Self & Mutual Inductance (LR Circuits & Growth/Decay)", isCompleted = false, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "ε = -L di/dt, i(t) = I_0(1 - e^(-t/τ)), τ = L/R"),
        SyllabusTopic("p10_t4", "Series LCR AC Circuit, Phasor Diagrams & Resonance", isCompleted = false, isHighYield = true, tag = "High Yield", keyFormulaHint = "Z = √(R² + (X_L - X_C)²), ω₀ = 1/√(LC)"),
        SyllabusTopic("p10_t5", "Quality Factor (Q), Power Factor & Ideal Transformers", isCompleted = false, isHighYield = false, tag = "Core", keyFormulaHint = "P_avg = V_rms I_rms cosφ, Q = ω₀ L / R")
      )
    ),
    Chapter(
      id = "p11",
      subject = SubjectType.PHYSICS,
      name = "Ray & Wave Optics",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 28,
      totalPyqs = 50,
      status = ChapterStatus.REVISED,
      revisionCount = 2,
      keyFormulaCount = 19,
      keyConcepts = listOf("Lens Maker Formula", "TIR & Prisms", "Young's Double Slit YDSE", "Diffraction Minima"),
      topics = listOf(
        SyllabusTopic("p11_t1", "Total Internal Reflection (TIR), Optical Fiber & Prisms", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "sin θ_c = 1/μ, δ = i + e - A"),
        SyllabusTopic("p11_t2", "Refraction at Spherical Surfaces & Lens Maker's Formula", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "μ2/v - μ1/u = (μ2-μ1)/R, 1/f = (μ-1)(1/R1 - 1/R2)"),
        SyllabusTopic("p11_t3", "Optical Instruments (Microscope, Astronomical Telescope Magnification)", isCompleted = false, isHighYield = false, tag = "NCERT Must", keyFormulaHint = "m_telescope = -f_o / f_e (normal)"),
        SyllabusTopic("p11_t4", "Huygens Principle & Wavefront Interference in YDSE", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "β = λ D / d, Path Diff Δx = d sinθ"),
        SyllabusTopic("p11_t5", "Single Slit Fraunhofer Diffraction & Resolving Limit", isCompleted = false, isHighYield = false, tag = "Formula Heavy", keyFormulaHint = "Minima: a sinθ = n λ, Central Width = 2 λ D / a")
      )
    ),
    Chapter(
      id = "p12",
      subject = SubjectType.PHYSICS,
      name = "Modern Physics (Photoelectric & Nuclei)",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 52,
      totalPyqs = 55,
      status = ChapterStatus.MASTERED,
      revisionCount = 4,
      keyFormulaCount = 15,
      keyConcepts = listOf("Einstein's Photoelectric Equation", "Bohr Atomic Model", "Radioactive Decay Law", "Binding Energy Curve"),
      topics = listOf(
        SyllabusTopic("p12_t1", "Photoelectric Effect, Stopping Potential & Work Function", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "K_max = h ν - Φ = e V_s"),
        SyllabusTopic("p12_t2", "de Broglie Wavelength of Matter & Davisson-Germer", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "λ = h / p = 12.27 / √V Å (electron)"),
        SyllabusTopic("p12_t3", "Bohr Model of Hydrogen-Like Atoms & Emission Spectra", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "r_n = 0.529 n²/Z Å, E_n = -13.6 Z²/n² eV"),
        SyllabusTopic("p12_t4", "Nuclear Radius, Mass Defect & Binding Energy Curve", isCompleted = true, isHighYield = true, tag = "Core", keyFormulaHint = "R = R₀ A^(1/3), ΔE = Δm · 931.5 MeV"),
        SyllabusTopic("p12_t5", "Radioactive Decay Law, Half-Life & Activity (N = N0 e^(-λt))", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "t_1/2 = 0.693 / λ, A = λ N")
      )
    ),

    // Chemistry - Class 11
    Chapter(
      id = "c1",
      subject = SubjectType.CHEMISTRY,
      name = "Atomic Structure & Quantum Numbers",
      classLevel = 11,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 44,
      totalPyqs = 50,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 12,
      keyConcepts = listOf("de Broglie Wavelength", "Heisenberg Uncertainty", "Schrodinger Wave Function", "Electronic Configurations"),
      topics = listOf(
        SyllabusTopic("c1_t1", "Bohr Radius, Energy Levels & Hydrogen Rydberg Lines", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "1/λ = R_H Z² (1/n1² - 1/n2²)"),
        SyllabusTopic("c1_t2", "de Broglie Dual Nature & Heisenberg's Uncertainty Principle", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "Δx · Δp ≥ h / (4π)"),
        SyllabusTopic("c1_t3", "Quantum Numbers (n, l, m_l, m_s) & Shapes of Orbitals", isCompleted = true, isHighYield = true, tag = "Core", keyFormulaHint = "Nodes: Radial = n-l-1, Angular = l"),
        SyllabusTopic("c1_t4", "Aufbau, Hund's Rule of Multiplicity & Pauli Exclusion", isCompleted = true, isHighYield = false, tag = "NCERT Must", keyFormulaHint = "(n + l) rule for orbital energy")
      )
    ),
    Chapter(
      id = "c2",
      subject = SubjectType.CHEMISTRY,
      name = "Chemical Bonding & Molecular Structure",
      classLevel = 11,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 55,
      totalPyqs = 60,
      status = ChapterStatus.MASTERED,
      revisionCount = 4,
      keyFormulaCount = 18,
      keyConcepts = listOf("VSEPR Geometry", "Hybridization sp3d/sp3d2", "Molecular Orbital Theory MOT", "Hydrogen Bonding"),
      topics = listOf(
        SyllabusTopic("c2_t1", "VSEPR Theory & 3D Molecular Geometry (Lone Pair Repulsion)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "lp-lp > lp-bp > bp-bp repulsion"),
        SyllabusTopic("c2_t2", "Hybridization (sp, sp², sp³, sp³d, sp³d²) & Steric Number", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "SN = 1/2 [V + M - C + A]"),
        SyllabusTopic("c2_t3", "Molecular Orbital Theory (MOT) & Bond Order Calculation", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "Bond Order = 1/2 [N_b - N_a], Paramagnetism rule"),
        SyllabusTopic("c2_t4", "Dipole Moment, Percent Ionic Character & Fajan's Rules", isCompleted = true, isHighYield = true, tag = "Concept", keyFormulaHint = "μ = q × d, Covalent character ↑ with small cation"),
        SyllabusTopic("c2_t5", "Intermolecular & Intramolecular Hydrogen Bonding Trends", isCompleted = true, isHighYield = false, tag = "NCERT Must", keyFormulaHint = "Boiling point anomalies (H2O vs H2S)")
      )
    ),
    Chapter(
      id = "c3",
      subject = SubjectType.CHEMISTRY,
      name = "Chemical & Ionic Equilibrium",
      classLevel = 11,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 40,
      totalPyqs = 50,
      status = ChapterStatus.REVISED,
      revisionCount = 2,
      keyFormulaCount = 16,
      keyConcepts = listOf("Le Chatelier's Principle", "pH of Buffer Solutions", "Solubility Product Ksp", "Salt Hydrolysis"),
      topics = listOf(
        SyllabusTopic("c3_t1", "Law of Mass Action, K_p vs K_c Relation & Reaction Quotient Q", isCompleted = true, isHighYield = true, tag = "Core", keyFormulaHint = "K_p = K_c (R T)^(Δn_g)"),
        SyllabusTopic("c3_t2", "Le Chatelier's Principle (Effect of P, T, Catalyst & Inert Gas)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Exothermic: T ↑ shifts backward"),
        SyllabusTopic("c3_t3", "Ostwald's Dilution Law & pH of Weak Acids/Bases", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "pH = 1/2 [pK_a - log C]"),
        SyllabusTopic("c3_t4", "Buffer Solutions (Henderson-Hasselbalch Equation)", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "pH = pK_a + log ([Conjugate Base] / [Acid])"),
        SyllabusTopic("c3_t5", "Solubility Product (K_sp) & Common Ion Precipitation", isCompleted = false, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Precipitation occurs if Q_sp > K_sp")
      )
    ),
    Chapter(
      id = "c4",
      subject = SubjectType.CHEMISTRY,
      name = "Thermodynamics & Thermochemistry",
      classLevel = 11,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 38,
      totalPyqs = 45,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 14,
      keyConcepts = listOf("Enthalpy ΔH", "Gibbs Free Energy ΔG=-nFE", "Hess's Law", "Bond Dissociation Energy"),
      topics = listOf(
        SyllabusTopic("c4_t1", "State Functions, First Law of Thermodynamics (ΔU = q + w)", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "ΔH = ΔU + Δn_g R T"),
        SyllabusTopic("c4_t2", "Hess's Law of Constant Heat Summation & Resonance Energy", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "ΔH_rxn = Σ B.E.(Reactants) - Σ B.E.(Products)"),
        SyllabusTopic("c4_t3", "Entropy (ΔS), Spontaneity & Second Law of Thermodynamics", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "ΔS_total = ΔS_sys + ΔS_surr > 0"),
        SyllabusTopic("c4_t4", "Gibbs Free Energy (ΔG = ΔH - TΔS) & Equilibrium Constant", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "ΔG° = -R T ln K_eq = -n F E°_cell")
      )
    ),
    Chapter(
      id = "c5",
      subject = SubjectType.CHEMISTRY,
      name = "Periodic Properties & Trends",
      classLevel = 11,
      weightage = WeightageLevel.MEDIUM,
      pyqsSolved = 35,
      totalPyqs = 40,
      status = ChapterStatus.MASTERED,
      revisionCount = 2,
      keyFormulaCount = 8,
      keyConcepts = listOf("Ionization Enthalpy", "Electronegativity", "Lanthanoid Contraction", "Electron Gain Enthalpy"),
      topics = listOf(
        SyllabusTopic("c5_t1", "Atomic & Ionic Radii Trends (Isoelectronic Species Order)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Radius ∝ 1 / Z_eff"),
        SyllabusTopic("c5_t2", "Ionization Enthalpy Anomalies (Be vs B, N vs O half-filled)", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "Half-filled & full-filled stability"),
        SyllabusTopic("c5_t3", "Electron Gain Enthalpy (Cl > F & S > O anomaly)", isCompleted = true, isHighYield = true, tag = "NCERT Must", keyFormulaHint = "2p inter-electronic repulsions in F"),
        SyllabusTopic("c5_t4", "Electronegativity Scales (Pauling & Mulliken) & Valency Trends", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "χ_Pauling = 0.208 √(Δ) eV")
      )
    ),
    Chapter(
      id = "c6",
      subject = SubjectType.CHEMISTRY,
      name = "General Organic Chemistry (GOC)",
      classLevel = 11,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 60,
      totalPyqs = 65,
      status = ChapterStatus.MASTERED,
      revisionCount = 4,
      keyFormulaCount = 22,
      keyConcepts = listOf("Inductive & Mesomeric Effects", "Hyperconjugation", "Carbocation Stability", "Aromaticity (Huckel Rule)"),
      topics = listOf(
        SyllabusTopic("c6_t1", "Electronic Effects: Inductive (+I/-I) & Electromeric", isCompleted = true, isHighYield = false, tag = "Foundational", keyFormulaHint = "-NO2 > -CN > -COOH > -F > -Cl"),
        SyllabusTopic("c6_t2", "Resonance, Mesomeric (+M/-M) & Extended Conjugation", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Octet complete > more covalent bonds"),
        SyllabusTopic("c6_t3", "Hyperconjugation (α-Hydrogen Count) & Heat of Hydrogenation", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "Stability of alkene ∝ number of α-H"),
        SyllabusTopic("c6_t4", "Aromaticity, Anti-Aromaticity & Non-Aromaticity (Hückel 4n+2 Rule)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Planar cyclic conjugated (4n+2) π electrons"),
        SyllabusTopic("c6_t5", "Carbocation, Carbanion & Free Radical Relative Stabilities", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "Carbocation: 3° > 2° > 1° (hyperconjugation + resonance)"),
        SyllabusTopic("c6_t6", "Acidic & Basic Strength Comparisons in Substituted Phenols/Amines", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Acidity ∝ -M / -I; Basicity in aq: 2° > 1° > 3° (Me)")
      )
    ),

    // Chemistry - Class 12
    Chapter(
      id = "c7",
      subject = SubjectType.CHEMISTRY,
      name = "Electrochemistry & Nernst Equation",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 42,
      totalPyqs = 48,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 15,
      keyConcepts = listOf("Nernst Cell EMF", "Kohlrausch's Law", "Faraday's Electrolysis", "Batteries & Corrosion"),
      topics = listOf(
        SyllabusTopic("c7_t1", "Galvanic Cells, Standard Electrode Potentials (E°_cell = E°_c - E°_a)", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "ΔG° = -n F E°_cell"),
        SyllabusTopic("c7_t2", "Nernst Equation at 298K for Single Electrodes & Complete Cells", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "E_cell = E°_cell - (0.0591/n) log Q"),
        SyllabusTopic("c7_t3", "Electrolytic Conductance, Molar Conductivity (Λ_m) & Kohlrausch Law", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "Λ_m° = v_+ λ_+° + v_- λ_-°, α = Λ_m / Λ_m°"),
        SyllabusTopic("c7_t4", "Faraday's First & Second Laws of Electrolysis", isCompleted = true, isHighYield = true, tag = "Core", keyFormulaHint = "w = Z I t = (E / 96500) I t"),
        SyllabusTopic("c7_t5", "Commercial Batteries (Lead Storage, Fuel Cell) & Rusting Mechanism", isCompleted = true, isHighYield = false, tag = "NCERT Must", keyFormulaHint = "H2-O2 fuel cell: 2H2 + O2 -> 2H2O")
      )
    ),
    Chapter(
      id = "c8",
      subject = SubjectType.CHEMISTRY,
      name = "Chemical Kinetics",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 38,
      totalPyqs = 42,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 12,
      keyConcepts = listOf("Integrated Rate Law", "Arrhenius Equation Activation Energy", "Half-Life Calculations"),
      topics = listOf(
        SyllabusTopic("c8_t1", "Rate of Reaction, Order vs Molecularity & Initial Rate Method", isCompleted = true, isHighYield = true, tag = "Core", keyFormulaHint = "Rate = k [A]^x [B]^y"),
        SyllabusTopic("c8_t2", "Zero, First & Second Order Integrated Rate Laws", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "1st Order: k = 2.303/t log(a/(a-x)), t_1/2 = 0.693/k"),
        SyllabusTopic("c8_t3", "Arrhenius Equation, Temperature Dependence & Activation Energy", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "log(k2/k1) = E_a/(2.303 R) [1/T1 - 1/T2]"),
        SyllabusTopic("c8_t4", "Pseudo First Order Reactions & Collision Theory Mechanism", isCompleted = true, isHighYield = false, tag = "Concept", keyFormulaHint = "Hydrolysis of ester in excess water")
      )
    ),
    Chapter(
      id = "c9",
      subject = SubjectType.CHEMISTRY,
      name = "Coordination Compounds",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 45,
      totalPyqs = 50,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 17,
      keyConcepts = listOf("Crystal Field Theory (CFT)", "IUPAC Naming", "Isomerism", "Magnetic Moments"),
      topics = listOf(
        SyllabusTopic("c9_t1", "Werner's Theory, Primary/Secondary Valencies & IUPAC Nomenclature", isCompleted = true, isHighYield = true, tag = "Core", keyFormulaHint = "Naming cation first, ligands alphabetical"),
        SyllabusTopic("c9_t2", "Structural (Ionization, Hydrate, Linkage) & Stereoisomerism (Geom & Optical)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "cis-[Co(en)2Cl2]+ is optically active"),
        SyllabusTopic("c9_t3", "Valence Bond Theory (VBT) Inner/Outer Orbital Complexes", isCompleted = true, isHighYield = false, tag = "Foundational", keyFormulaHint = "d²sp³ vs sp³d² hybridization"),
        SyllabusTopic("c9_t4", "Crystal Field Theory (CFT) Octahedral/Tetrahedral Splitting (Δ_o, Δ_t)", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "Δ_t = 4/9 Δ_o, Strong field: Pairing Δ_o > P"),
        SyllabusTopic("c9_t5", "Spectrochemical Series & Spin-Only Magnetic Moment (μ = √(n(n+2)))", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "CO > CN- > en > NH3 > H2O > F- > Cl- > Br- > I-")
      )
    ),
    Chapter(
      id = "c10",
      subject = SubjectType.CHEMISTRY,
      name = "p-Block & d/f-Block Elements",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 30,
      totalPyqs = 55,
      status = ChapterStatus.IN_PROGRESS,
      revisionCount = 1,
      keyFormulaCount = 14,
      keyConcepts = listOf("Transition Metal Properties", "KMnO4 & K2Cr2O7 Reactions", "Oxoacids of Phosphorus/Sulfur"),
      topics = listOf(
        SyllabusTopic("c10_t1", "Group 15, 16, 17 Oxoacids Structures, Oxidation States & Basicity", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "H3PO2 (mono), H3PO3 (di), H3PO4 (tribasic)"),
        SyllabusTopic("c10_t2", "Interhalogen Compounds & Noble Gas Compounds (XeF2, XeF4, XeO3)", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "XeF4 square planar with 2 lone pairs"),
        SyllabusTopic("c10_t3", "3d Transition Series Trends (Catalytic, Alloy, Variable Valency)", isCompleted = false, isHighYield = false, tag = "NCERT Must", keyFormulaHint = "d-d transitions for color"),
        SyllabusTopic("c10_t4", "Potassium Permanganate (KMnO4) & Dichromate (K2Cr2O7) Redox Reactions", isCompleted = false, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "MnO4- in acidic: n-factor = 5 (Mn2+)"),
        SyllabusTopic("c10_t5", "Lanthanoid & Actinoid Contraction & Chemical Consequences", isCompleted = false, isHighYield = true, tag = "NCERT Must", keyFormulaHint = "Zr and Hf similar atomic radii due to 4f shielding")
      )
    ),
    Chapter(
      id = "c11",
      subject = SubjectType.CHEMISTRY,
      name = "Aldehydes, Ketones & Carboxylic Acids",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 48,
      totalPyqs = 55,
      status = ChapterStatus.REVISED,
      revisionCount = 3,
      keyFormulaCount = 24,
      keyConcepts = listOf("Aldol & Cannizzaro Reactions", "Ozonolysis", "Grignard Reactions", "HVZ Reaction"),
      topics = listOf(
        SyllabusTopic("c11_t1", "Nucleophilic Addition to Carbonyls (HCN, NaHSO3, Grignard RMgX)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Reactivity: HCHO > RCHO > RCOR"),
        SyllabusTopic("c11_t2", "Aldol & Cross-Aldol Condensation (Enolate Mechanisms)", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "Requires α-H; dil. NaOH then heat gives enone"),
        SyllabusTopic("c11_t3", "Cannizzaro Reaction & Disproportionation of Non-Enolizable Aldehydes", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Con. KOH with HCHO or PhCHO gives alcohol + salt"),
        SyllabusTopic("c11_t4", "Tollens, Fehling & Iodoform (Haloform) Test for Methyl Ketones", isCompleted = true, isHighYield = true, tag = "Lab Tests", keyFormulaHint = "I2 + NaOH gives yellow CHI3 ppt for CH3-C=O"),
        SyllabusTopic("c11_t5", "Carboxylic Acid Derivatives, Decarboxylation & Hell-Volhard-Zelinsky (HVZ)", isCompleted = false, isHighYield = false, tag = "Named Reaction", keyFormulaHint = "R-CH2-COOH + Br2/red P gives α-bromo acid")
      )
    ),
    Chapter(
      id = "c12",
      subject = SubjectType.CHEMISTRY,
      name = "Amines, Diazonium Salts & Biomolecules",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 36,
      totalPyqs = 45,
      status = ChapterStatus.REVISED,
      revisionCount = 2,
      keyFormulaCount = 16,
      keyConcepts = listOf("Hoffmann Bromamide", "Sandmeyer Reaction", "Peptide Bonds & DNA/RNA"),
      topics = listOf(
        SyllabusTopic("c12_t1", "Hoffmann Bromamide Degradation & Gabriel Phthalimide Synthesis", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "RCONH2 + Br2 + 4KOH -> R-NH2 (1 carbon less)"),
        SyllabusTopic("c12_t2", "Hinsberg's Test for 1°, 2°, 3° Amines & Carbylamine Reaction", isCompleted = true, isHighYield = true, tag = "Lab Tests", keyFormulaHint = "Benzenesulfonyl chloride solubility"),
        SyllabusTopic("c12_t3", "Benzene Diazonium Chloride Reactions (Sandmeyer, Gattermann, Coupling)", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "Ph-N2+ Cl- + CuCl/HCl -> Ph-Cl + N2"),
        SyllabusTopic("c12_t4", "Carbohydrates: Glucose/Fructose Structures, Mutarotation & Glycosidic Linkages", isCompleted = false, isHighYield = true, tag = "NCERT Must", keyFormulaHint = "Reducing sugars give Tollens positive"),
        SyllabusTopic("c12_t5", "Proteins (Peptide Linkage, Denaturation, Zwitterions) & Nucleic Acids (DNA/RNA Bases)", isCompleted = false, isHighYield = false, tag = "NCERT Must", keyFormulaHint = "A=T, G≡C purine/pyrimidine hydrogen bonds")
      )
    ),

    // Mathematics - Class 11
    Chapter(
      id = "m1",
      subject = SubjectType.MATHEMATICS,
      name = "Quadratic Equations & Complex Numbers",
      classLevel = 11,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 40,
      totalPyqs = 48,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 14,
      keyConcepts = listOf("Location of Roots", "Euler's Form e^(iθ)", "De Moivre's Theorem", "Geometry of Complex Plane"),
      topics = listOf(
        SyllabusTopic("m1_t1", "Nature of Roots, Discriminant & Symmetric Relations (α+β, αβ)", isCompleted = true, isHighYield = false, tag = "Foundational", keyFormulaHint = "x = (-b ± √D)/(2a)"),
        SyllabusTopic("m1_t2", "Location of Roots (Both roots > k, k between roots, etc.)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Conditions on D, -b/(2a), and a·f(k)"),
        SyllabusTopic("m1_t3", "Complex Number Modulus, Argument & Polar/Euler Form (r e^(iθ))", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "e^(iθ) = cosθ + i sinθ"),
        SyllabusTopic("m1_t4", "De Moivre's Theorem & Cube Roots of Unity (1, ω, ω²)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "1 + ω + ω² = 0, ω³ = 1"),
        SyllabusTopic("m1_t5", "Geometry in Argand Plane (Circles |z-z0|=r, Perpendicular Bisectors, Concyclicity)", isCompleted = true, isHighYield = true, tag = "Advance Focus", keyFormulaHint = "|z - z1| / |z - z2| = k represents circle (k≠1)")
      )
    ),
    Chapter(
      id = "m2",
      subject = SubjectType.MATHEMATICS,
      name = "Sequence & Series (AP, GP, AGP)",
      classLevel = 11,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 35,
      totalPyqs = 42,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 12,
      keyConcepts = listOf("Sum of AGP", "AM-GM Inequality", "Telescopic Summation", "Special Series Σn^3"),
      topics = listOf(
        SyllabusTopic("m2_t1", "Arithmetic (AP) & Geometric Progressions (GP) Sums", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "S_n = n/2[2a+(n-1)d], S_inf = a/(1-r)"),
        SyllabusTopic("m2_t2", "Arithmetico-Geometric Progression (AGP) Summation Technique", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Multiply by r and subtract (1-r)S method"),
        SyllabusTopic("m2_t3", "AM-GM-HM Inequalities & Minima-Maxima Optimization", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "AM ≥ GM ≥ HM for positive numbers"),
        SyllabusTopic("m2_t4", "Sigma Notation & Telescopic Cancellation (Method of Differences)", isCompleted = true, isHighYield = true, tag = "Advance Focus", keyFormulaHint = "Σ (V_n - V_{n-1}) = V_N - V_0")
      )
    ),
    Chapter(
      id = "m3",
      subject = SubjectType.MATHEMATICS,
      name = "Permutations, Combinations & Binomial",
      classLevel = 11,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 42,
      totalPyqs = 50,
      status = ChapterStatus.REVISED,
      revisionCount = 2,
      keyFormulaCount = 16,
      keyConcepts = listOf("Multinomial Theorem", "Derangements", "Binomial Coefficients Properties", "Divisors & Grid Paths"),
      topics = listOf(
        SyllabusTopic("m3_t1", "Fundamental Counting Principle, nPr and nCr Selections", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "nCr = n! / (r! (n-r)!)"),
        SyllabusTopic("m3_t2", "Circular Permutations, Group Distribution & Derangements", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "D_n = n! [1 - 1/1! + 1/2! - ... + (-1)^n/n!]"),
        SyllabusTopic("m3_t3", "Multinomial Theorem & Beggar's Method (Non-negative Integer Solutions)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "x1+x2+...+xr = n -> (n+r-1)C(r-1)"),
        SyllabusTopic("m3_t4", "Binomial Expansion General Term & Greatest Binomial Coefficient", isCompleted = true, isHighYield = true, tag = "Core", keyFormulaHint = "T_{r+1} = nCr a^(n-r) b^r"),
        SyllabusTopic("m3_t5", "Sum of Series Involving Binomial Coefficients & Calculus in Binomial", isCompleted = false, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Σ nCr = 2^n, Σ r nCr = n 2^(n-1)")
      )
    ),
    Chapter(
      id = "m4",
      subject = SubjectType.MATHEMATICS,
      name = "Straight Lines & Circles",
      classLevel = 11,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 48,
      totalPyqs = 55,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 20,
      keyConcepts = listOf("Family of Circles", "Chord of Contact", "Director Circle", "Pair of Straight Lines"),
      topics = listOf(
        SyllabusTopic("m4_t1", "Slope Forms, Perpendicular Distance & Angle Bisector Equations", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "d = |ax1 + by1 + c| / √(a² + b²)"),
        SyllabusTopic("m4_t2", "Pair of Straight Lines Passing Through Origin & Homogenization", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "ax² + 2hxy + by² = 0, tanθ = 2√(h²-ab)/(a+b)"),
        SyllabusTopic("m4_t3", "Standard Circle Equation, Tangents (Slope & Point Form T=0)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "y = mx ± r√(1+m²), T: xx1 + yy1 = r²"),
        SyllabusTopic("m4_t4", "Chord of Contact (T=0) & Chord with Given Midpoint (T=S1)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Length of Tangent = √S1"),
        SyllabusTopic("m4_t5", "Family of Circles (S + λL = 0) & Radical Axis of Two Circles", isCompleted = true, isHighYield = true, tag = "Advance Focus", keyFormulaHint = "Radical Axis: S1 - S2 = 0")
      )
    ),
    Chapter(
      id = "m5",
      subject = SubjectType.MATHEMATICS,
      name = "Conic Sections (Parabola, Ellipse, Hyperbola)",
      classLevel = 11,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 36,
      totalPyqs = 52,
      status = ChapterStatus.IN_PROGRESS,
      revisionCount = 1,
      keyFormulaCount = 25,
      keyConcepts = listOf("Tangents & Normals", "Auxiliary Circle", "Eccentricity & Directrix", "Asymptotes of Hyperbola"),
      topics = listOf(
        SyllabusTopic("m5_t1", "Parabola Standard Forms (y² = 4ax), Focus, Directrix & Latus Rectum", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Parametric: (at², 2at), Tangent: y = mx + a/m"),
        SyllabusTopic("m5_t2", "Normals to Parabola & Three Normals Passing Through Common Point", isCompleted = true, isHighYield = true, tag = "Advance Focus", keyFormulaHint = "Normal: y = mx - 2am - am³"),
        SyllabusTopic("m5_t3", "Ellipse (x²/a² + y²/b² = 1), Eccentricity (e < 1) & Director Circle", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "b² = a²(1-e²), Director circle: x² + y² = a² + b²"),
        SyllabusTopic("m5_t4", "Hyperbola (x²/a² - y²/b² = 1), Rectangular Hyperbola & Asymptotes", isCompleted = false, isHighYield = true, tag = "High Yield", keyFormulaHint = "b² = a²(e²-1), xy = c² parametric: (ct, c/t)"),
        SyllabusTopic("m5_t5", "Common Tangents to Conics & Optical Reflection Properties", isCompleted = false, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Equate discriminant D = 0 or use slope form")
      )
    ),
    Chapter(
      id = "m6",
      subject = SubjectType.MATHEMATICS,
      name = "Trigonometric Equations & Identities",
      classLevel = 11,
      weightage = WeightageLevel.MEDIUM,
      pyqsSolved = 30,
      totalPyqs = 40,
      status = ChapterStatus.MASTERED,
      revisionCount = 2,
      keyFormulaCount = 15,
      keyConcepts = listOf("General Solutions", "Multiple & Sub-multiple Angles", "Properties of Triangles"),
      topics = listOf(
        SyllabusTopic("m6_t1", "Compound Angles & Transformation Formulas (sin(A±B), cos(A±B))", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "2 sinA cosB = sin(A+B) + sin(A-B)"),
        SyllabusTopic("m6_t2", "Multiple & Half Angles (sin 2θ, cos 2θ, tan 3θ formulas)", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "cos 2θ = cos²θ - sin²θ = 2cos²θ - 1"),
        SyllabusTopic("m6_t3", "General Solutions of Trigonometric Equations (sinθ=sinα, etc.)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "θ = nπ + (-1)^n α, θ = 2nπ ± α"),
        SyllabusTopic("m6_t4", "Inverse Trigonometric Functions (ITF) Domains, Principal Ranges & Identities", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "tan⁻¹x + tan⁻¹y = tan⁻¹((x+y)/(1-xy)) for xy<1")
      )
    ),

    // Mathematics - Class 12
    Chapter(
      id = "m7",
      subject = SubjectType.MATHEMATICS,
      name = "Functions, Limits & Continuity",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 50,
      totalPyqs = 58,
      status = ChapterStatus.MASTERED,
      revisionCount = 4,
      keyFormulaCount = 18,
      keyConcepts = listOf("L'Hopital's Rule", "Standard Limits", "Intermediate Value Theorem", "Domain & Range"),
      topics = listOf(
        SyllabusTopic("m7_t1", "Functions Domain, Range, Injective/Surjective & Composite Functions", isCompleted = true, isHighYield = true, tag = "Core", keyFormulaHint = "f(x) invertible iff bijective (one-one onto)"),
        SyllabusTopic("m7_t2", "Even/Odd & Periodic Functions Fundamental Periods", isCompleted = true, isHighYield = false, tag = "Concept", keyFormulaHint = "f(-x) = f(x) even, f(x+T) = f(x) periodic"),
        SyllabusTopic("m7_t3", "Standard Limits (0/0, ∞/∞, 1^∞ forms) & Expansion Series", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "lim (1+f(x))^(1/g(x)) = e^(lim f(x)/g(x))"),
        SyllabusTopic("m7_t4", "L'Hôpital's Rule & Continuity Definition (LHL = RHL = f(a))", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "Differentiate numerator & denominator independently"),
        SyllabusTopic("m7_t5", "Differentiability at a Point (LHD vs RHD) & Intermediate Value Theorem", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "f'(a-) = f'(a+) must be finite and equal")
      )
    ),
    Chapter(
      id = "m8",
      subject = SubjectType.MATHEMATICS,
      name = "Differentiation & Application of Derivatives (AOD)",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 52,
      totalPyqs = 60,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 22,
      keyConcepts = listOf("Rolle's & LMVT", "Maxima-Minima Word Problems", "Tangents & Normals", "Monotonicity"),
      topics = listOf(
        SyllabusTopic("m8_t1", "Chain Rule, Implicit & Parametric Differentiation Derivatives", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "d²y/dx² = [d/dt(dy/dx)] / (dx/dt)"),
        SyllabusTopic("m8_t2", "Rate Measure, Tangents & Normals Slope Equations", isCompleted = true, isHighYield = true, tag = "Core", keyFormulaHint = "m_t = dy/dx, m_n = -1/(dy/dx)"),
        SyllabusTopic("m8_t3", "Monotonicity (Increasing/Decreasing) & Intervals Determination", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "f'(x) ≥ 0 for strictly increasing"),
        SyllabusTopic("m8_t4", "Rolle's Theorem & Lagrange's Mean Value Theorem (LMVT)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "f'(c) = [f(b) - f(a)] / (b - a)"),
        SyllabusTopic("m8_t5", "Maxima & Minima (First/Second Derivative Test & Global Extrema)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "f'(x)=0; f''(x)<0 (local max), f''(x)>0 (local min)")
      )
    ),
    Chapter(
      id = "m9",
      subject = SubjectType.MATHEMATICS,
      name = "Definite & Indefinite Integration",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 45,
      totalPyqs = 62,
      status = ChapterStatus.REVISED,
      revisionCount = 2,
      keyFormulaCount = 28,
      keyConcepts = listOf("King's Property ∫f(x)dx", "Leibnitz Rule of Differentiation", "Reduction Formulas", "By Parts"),
      topics = listOf(
        SyllabusTopic("m9_t1", "Standard Substitution & Integration by Parts (ILATE Rule)", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "∫ u v dx = u ∫ v dx - ∫ (u' ∫ v dx) dx"),
        SyllabusTopic("m9_t2", "Special Trigonometric Integrals & Partial Fractions", isCompleted = true, isHighYield = false, tag = "Technique", keyFormulaHint = "∫ dx/(a + b cos²x) -> divide by cos²x"),
        SyllabusTopic("m9_t3", "King's Rule of Definite Integrals (∫_a^b f(x)dx = ∫_a^b f(a+b-x)dx)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "I + I = ∫_a^b [f(x) + f(a+b-x)] dx"),
        SyllabusTopic("m9_t4", "Leibniz Integral Rule for Differentiation Under Integral Sign", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "d/dx ∫_{u(x)}^{v(x)} f(t) dt = f(v)v' - f(u)u'"),
        SyllabusTopic("m9_t5", "Definite Integral as Limit of a Riemann Sum (Σ (1/n) f(r/n))", isCompleted = false, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "lim 1/n Σ f(r/n) = ∫₀¹ f(x) dx"),
        SyllabusTopic("m9_t6", "Reduction Formulas & Periodic Function Definite Integrals", isCompleted = false, isHighYield = false, tag = "Advance Focus", keyFormulaHint = "∫₀^{nT} f(x)dx = n ∫₀^T f(x)dx")
      )
    ),
    Chapter(
      id = "m10",
      subject = SubjectType.MATHEMATICS,
      name = "Differential Equations & Area under Curves",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 38,
      totalPyqs = 45,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 14,
      keyConcepts = listOf("Linear Differential Equations LDE", "Integrating Factor (IF)", "Homogeneous Equations", "Bounding Parabola & Lines"),
      topics = listOf(
        SyllabusTopic("m10_t1", "Order & Degree of Differential Equations & Variable Separable", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "∫ f(y) dy = ∫ g(x) dx + C"),
        SyllabusTopic("m10_t2", "Homogeneous Differential Equations (y = v x substitution)", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "dy/dx = v + x dv/dx"),
        SyllabusTopic("m10_t3", "Linear Differential Equations (LDE) & Integrating Factor (IF)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "dy/dx + P y = Q -> IF = e^(∫ P dx), y · IF = ∫ (Q · IF) dx"),
        SyllabusTopic("m10_t4", "Area Enclosed Between Curves, Parabolas & Straight Lines", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Area = ∫ |y_upper - y_lower| dx")
      )
    ),
    Chapter(
      id = "m11",
      subject = SubjectType.MATHEMATICS,
      name = "Vectors & 3D Geometry",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 46,
      totalPyqs = 50,
      status = ChapterStatus.MASTERED,
      revisionCount = 4,
      keyFormulaCount = 20,
      keyConcepts = listOf("Scalar Triple Product (STP)", "Vector Triple Product (VTP)", "Shortest Distance Between Skew Lines", "Plane Equations"),
      topics = listOf(
        SyllabusTopic("m11_t1", "Dot Product, Cross Product & Projection of Vector on Line", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "a · b = |a||b| cosθ, proj = (a · b) / |b|"),
        SyllabusTopic("m11_t2", "Scalar Triple Product [a b c] & Coplanarity Condition", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "[a b c] = a · (b × c) = det(matrix), [a b c]=0 -> coplanar"),
        SyllabusTopic("m11_t3", "Vector Triple Product a × (b × c) = (a·c)b - (a·b)c", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "'BAC minus CAB' rule"),
        SyllabusTopic("m11_t4", "3D Line Equations, Direction Cosines & Intersecting Lines", isCompleted = true, isHighYield = true, tag = "Core", keyFormulaHint = "r = a + λ b, l² + m² + n² = 1"),
        SyllabusTopic("m11_t5", "Shortest Distance Between Two Skew Lines in 3D Space", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "d = |(a2 - a1) · (b1 × b2)| / |b1 × b2|")
      )
    ),
    Chapter(
      id = "m12",
      subject = SubjectType.MATHEMATICS,
      name = "Matrices, Determinants & Probability",
      classLevel = 12,
      weightage = WeightageLevel.HIGH,
      pyqsSolved = 44,
      totalPyqs = 48,
      status = ChapterStatus.MASTERED,
      revisionCount = 3,
      keyFormulaCount = 18,
      keyConcepts = listOf("Cayley-Hamilton Theorem", "Cramer's Rule", "Bayes' Theorem", "Binomial Distribution"),
      topics = listOf(
        SyllabusTopic("m12_t1", "Matrix Multiplication, Symmetric/Skew-Symmetric & Inverse (A⁻¹)", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "A⁻¹ = adj(A) / det(A), |adj(A)| = |A|^(n-1)"),
        SyllabusTopic("m12_t2", "Cramer's Rule & System of Linear Equations (Unique, Infinite, No Sol)", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "Δ=0 & any Δx≠0 (No Sol), Δ=Δx=Δy=Δz=0 (Infinite)"),
        SyllabusTopic("m12_t3", "Conditional Probability & Multiplication Theorem", isCompleted = true, isHighYield = false, tag = "Core", keyFormulaHint = "P(A|B) = P(A ∩ B) / P(B)"),
        SyllabusTopic("m12_t4", "Bayes' Theorem & Total Probability Theorem", isCompleted = true, isHighYield = true, tag = "PYQ Hotspot", keyFormulaHint = "P(E_i|A) = P(E_i)P(A|E_i) / Σ P(E_k)P(A|E_k)"),
        SyllabusTopic("m12_t5", "Bernoulli Trials, Binomial Probability Distribution (n, p, q)", isCompleted = true, isHighYield = true, tag = "High Yield", keyFormulaHint = "P(X=r) = nCr p^r q^(n-r), Mean = np, Var = npq")
      )
    )
  )

  val dailyGoals: List<DailyGoal> = listOf(
    DailyGoal("g1", "Solve 25 PYQs from Rotational Dynamics (2020-2024)", SubjectType.PHYSICS, false, 45, true),
    DailyGoal("g2", "Revise Organic Chemistry Named Reactions & Mechanism Sheet", SubjectType.CHEMISTRY, true, 30, true),
    DailyGoal("g3", "Practice 20 Definite Integration King's Rule & Leibnitz Problems", SubjectType.MATHEMATICS, false, 50, true),
    DailyGoal("g4", "Review Error Notebook for Current Electricity & Potentiometer", SubjectType.PHYSICS, false, 25, false),
    DailyGoal("g5", "Quick Flashcard Quiz on Coordination Compounds Crystal Field Splitting", SubjectType.CHEMISTRY, true, 20, false)
  )

  val mockTests: List<MockTest> = listOf(
    MockTest("t1", "All India JEE Main Mock Test #14 (Full Syllabus)", "JEE Main", "Next Sunday (9:00 AM)", 7, 3.0, 300, 242, 99.35, true),
    MockTest("t2", "JEE Advanced Paper 1 & 2 Marathon Mock #06", "JEE Advanced", "2 Weeks (Both Sessions)", 14, 6.0, 360, 218, 98.90, true),
    MockTest("t3", "Physics & Mathematics Rigorous Sectional Drill #09", "JEE Main", "In 3 Days", 3, 2.0, 200, null, null, true),
    MockTest("t4", "Organic Chemistry High-Yield Speed Mock Test", "JEE Main", "Completed Yesterday", -1, 1.0, 100, 88, 99.60, false)
  )

  val formulas: List<FormulaCard> = listOf(
    FormulaCard("f1", SubjectType.PHYSICS, "Rotational Dynamics", "Parallel Axis Theorem", "I = I_cm + M * d^2", "Calculates moment of inertia about any parallel axis separated by distance d from center of mass axis.", "I: Moment of Inertia (kg·m²), M: Total Mass (kg), d: Distance (m)", true),
    FormulaCard("f2", SubjectType.PHYSICS, "Thermodynamics", "Carnot Engine Efficiency", "η = 1 - (T_cold / T_hot) = W / Q_in", "Maximum theoretical efficiency of any heat engine operating between two temperatures in Kelvin.", "T in Kelvin (K). Q_in and W in Joules (J)", true),
    FormulaCard("f3", SubjectType.PHYSICS, "Current Electricity", "Drift Velocity & Current Relation", "I = n * e * A * v_d  &  v_d = (e * E * τ) / m", "Relates macroscopic electric current to microscopic charge carrier density and relaxation time.", "n: free electron density (m⁻³), e = 1.6×10⁻¹⁹ C, A: area, τ: relaxation time", false),
    FormulaCard("f4", SubjectType.PHYSICS, "Optics", "Lens Maker's Equation", "1/f = (μ_rel - 1) * (1/R1 - 1/R2)", "Determines focal length of a thin lens immersed in a medium of refractive index μ_med.", "μ_rel = μ_lens / μ_medium. Follow Cartesian sign convention for R1, R2.", true),
    FormulaCard("f5", SubjectType.PHYSICS, "Modern Physics", "de Broglie Wavelength of Electron", "λ = h / p = 1.227 / sqrt(V) nm = 12.27 / sqrt(V) Å", "Quick shortcut for accelerating voltage V (in Volts) for non-relativistic electrons.", "V in Volts. h = 6.626×10⁻³⁴ J·s, m_e = 9.1×10⁻³¹ kg", true),
    FormulaCard("f6", SubjectType.PHYSICS, "Electrostatics", "Electric Field due to Uniform Charged Ring", "E = (k * Q * x) / (R^2 + x^2)^(3/2)", "Field on the axis at distance x. Maximum at x = R / sqrt(2), with E_max = 2kQ / (3*sqrt(3)*R^2).", "k = 1/(4πε₀) = 9×10⁹ N·m²/C²", false),

    FormulaCard("f7", SubjectType.CHEMISTRY, "Physical Chemistry", "Nernst Equation for Single Electrode", "E_cell = E°_cell - (0.0591 / n) * log10(Q)", "Computes cell potential at 298 K under non-standard concentration conditions.", "n: number of electrons exchanged, Q: reaction quotient [Products]/[Reactants]", true),
    FormulaCard("f8", SubjectType.CHEMISTRY, "Chemical Kinetics", "First Order Reaction Rate Law", "k = (2.303 / t) * log10(a / (a - x))  &  t_1/2 = 0.693 / k", "Independent of initial reactant concentration. Concentration decays exponentially: A_t = A_0 * e^(-kt).", "k in s⁻¹ or min⁻¹", true),
    FormulaCard("f9", SubjectType.CHEMISTRY, "Thermodynamics", "Gibbs Free Energy & Equilibrium", "ΔG° = -n * F * E°_cell = -2.303 * R * T * log10(K_eq)", "Spontaneity criterion: ΔG < 0 for spontaneous process at constant T and P.", "F = 96500 C/mol, R = 8.314 J/(mol·K)", false),
    FormulaCard("f10", SubjectType.CHEMISTRY, "Coordination Compounds", "Spin-Only Magnetic Moment", "μ_s = sqrt(n * (n + 2)) BM", "Determines paramagnetism based on number of unpaired d-electrons n in Bohr Magnetons.", "BM: Bohr Magneton (9.27×10⁻²⁴ J/T)", true),
    FormulaCard("f11", SubjectType.CHEMISTRY, "Organic Chemistry", "Aldol Condensation Product", "2 R-CH2-CHO --(dil. NaOH)--> R-CH2-CH(OH)-CH(R)-CHO --(Δ)--> α,β-unsaturated aldehyde", "Requires at least one α-hydrogen. Intermediary is an enolate carbanion.", "Dilute base (10% NaOH/Ba(OH)2), Heat eliminates water", true),

    FormulaCard("f12", SubjectType.MATHEMATICS, "Calculus", "Leibnitz Rule of Differentiation under Integral", "d/dx [ ∫_{u(x)}^{v(x)} f(t) dt ] = f(v(x)) * v'(x) - f(u(x)) * u'(x)", "Crucial for evaluating 0/0 limit problems using L'Hopital's rule involving definite integrals.", "u(x) and v(x) are differentiable functions", true),
    FormulaCard("f13", SubjectType.MATHEMATICS, "Definite Integrals", "King's Rule of Definite Integration", "∫_a^b f(x) dx = ∫_a^b f(a + b - x) dx", "The single most tested property in JEE Main & Advanced definite calculus.", "Adding original integral I to transformed I often simplifies the integrand to a constant.", true),
    FormulaCard("f14", SubjectType.MATHEMATICS, "3D Geometry", "Shortest Distance Between Skew Lines", "d = | ( (a2 - a1) · (b1 × b2) ) / |b1 × b2| |", "Calculates minimum perpendicular distance between non-parallel, non-intersecting 3D lines.", "r1 = a1 + λ b1,  r2 = a2 + μ b2", true),
    FormulaCard("f15", SubjectType.MATHEMATICS, "Vectors", "Vector Triple Product (VTP)", "a × (b × c) = (a · c) b - (a · b) c", "Remember as: 'BAC minus CAB'. The resulting vector lies in the plane of b and c.", "Vector identity. Note that (a × b) × c ≠ a × (b × c)", false),
    FormulaCard("f16", SubjectType.MATHEMATICS, "Complex Numbers", "Distance and Circle Representation", "|z - z0| = r  &  arg((z - z1)/(z - z2)) = θ", "Represents locus of z: circle of radius r centered at z0; circle arc subtending angle θ.", "z = x + iy", true)
  )

  val savedFormulaSheets: List<SavedFormulaSheet> = listOf(
    SavedFormulaSheet(
      id = "sheet_phy_mech",
      title = "Physics Mechanics & Rotational Super Sheet",
      subject = SubjectType.PHYSICS,
      description = "Complete formula reference for 1D/2D Kinematics, Newton's Laws, Work-Energy, Center of Mass & Moment of Inertia.",
      formulaCount = 5,
      formulaIds = listOf("f1", "f2"),
      isDownloadedOffline = true,
      category = "Mechanics",
      lastUpdatedFormatted = "Cached in Room DB",
      downloadSizeKb = 28
    ),
    SavedFormulaSheet(
      id = "sheet_phy_elec_optics",
      title = "Physics Electrodynamics & Optics Vault",
      subject = SubjectType.PHYSICS,
      description = "High-yield formulas for Drift Velocity, Ohm's Law, Lens Maker's Equation, de Broglie wavelength & Electric Fields.",
      formulaCount = 4,
      formulaIds = listOf("f3", "f4", "f5", "f6"),
      isDownloadedOffline = true,
      category = "Electrodynamics",
      lastUpdatedFormatted = "Cached in Room DB",
      downloadSizeKb = 34
    ),
    SavedFormulaSheet(
      id = "sheet_chem_physical",
      title = "Physical Chemistry High-Yield Compendium",
      subject = SubjectType.CHEMISTRY,
      description = "Essential formulas for Nernst Equation, Chemical Kinetics, Gibbs Free Energy & Colligative Solutions.",
      formulaCount = 3,
      formulaIds = listOf("f7", "f8", "f9"),
      isDownloadedOffline = true,
      category = "Physical Chemistry",
      lastUpdatedFormatted = "Cached in Room DB",
      downloadSizeKb = 26
    ),
    SavedFormulaSheet(
      id = "sheet_chem_inorg_org",
      title = "Inorganic & Organic Reactions Summary Sheet",
      subject = SubjectType.CHEMISTRY,
      description = "Coordination spin-only magnetic moments, Crystal Field Theory, Aldol Condensation & Named Reaction reagents.",
      formulaCount = 2,
      formulaIds = listOf("f10", "f11"),
      isDownloadedOffline = true,
      category = "Reactions & Reagents",
      lastUpdatedFormatted = "Cached in Room DB",
      downloadSizeKb = 22
    ),
    SavedFormulaSheet(
      id = "sheet_math_calculus",
      title = "Mathematics Calculus & Definite Integration Sheet",
      subject = SubjectType.MATHEMATICS,
      description = "King's Rule of Definite Integration, Leibnitz Rule under Integral sign, L'Hopital shortcuts & Area under curves.",
      formulaCount = 2,
      formulaIds = listOf("f12", "f13"),
      isDownloadedOffline = true,
      category = "Calculus",
      lastUpdatedFormatted = "Cached in Room DB",
      downloadSizeKb = 30
    ),
    SavedFormulaSheet(
      id = "sheet_math_vectors_3d",
      title = "Mathematics Vectors, 3D & Complex Numbers Sheet",
      subject = SubjectType.MATHEMATICS,
      description = "Shortest distance between skew lines, Vector Triple Product (BAC-CAB), Euler form & Argand plane loci.",
      formulaCount = 3,
      formulaIds = listOf("f14", "f15", "f16"),
      isDownloadedOffline = true,
      category = "Geometry & Vectors",
      lastUpdatedFormatted = "Cached in Room DB",
      downloadSizeKb = 36
    )
  )

  val colleges: List<College> = listOf(
    College(
      id = "iitb",
      name = "Indian Institute of Technology Bombay",
      shortName = "IIT Bombay",
      location = "Powai, Mumbai, Maharashtra",
      nirfRank = 3,
      established = 1958,
      avgPackageLpa = 23.5,
      highestPackageLpa = "₹3.67 Cr (International) / ₹1.68 Cr (Domestic)",
      cseCutoff = 67,
      eceCutoff = 380,
      mechCutoff = 1750,
      overview = "India's premier engineering powerhouse located beside Powai Lake. Known for world-class research, vibrant campus culture, Mood Indigo, and highest placement numbers.",
      keyBranches = listOf("Computer Science (CSE)", "Electrical Engineering", "Mechanical", "Aerospace", "Data Science & AI", "Chemical"),
      campusHighlights = listOf("Mood Indigo & Techfest festivals", "State of the art maker labs & supercomputing", "Powai Lakefront campus", "Top startup incubation SINE")
    ),
    College(
      id = "iitd",
      name = "Indian Institute of Technology Delhi",
      shortName = "IIT Delhi",
      location = "Hauz Khas, New Delhi",
      nirfRank = 2,
      established = 1961,
      avgPackageLpa = 24.2,
      highestPackageLpa = "₹2.4 Cr (International) / ₹1.45 Cr (Domestic)",
      cseCutoff = 115,
      eceCutoff = 580,
      mechCutoff = 1980,
      overview = "Located in the heart of the national capital. Unmatched alumni network, premier research centers, and the highest startup founder density in India.",
      keyBranches = listOf("Computer Science (CSE)", "Mathematics & Computing (MnC)", "Electrical", "Mechanical", "Biotech"),
      campusHighlights = listOf("Rendezvous Cultural Fest", "Tryst Techfest", "Proximity to top tech hubs", "Extensive international exchange")
    ),
    College(
      id = "iitm",
      name = "Indian Institute of Technology Madras",
      shortName = "IIT Madras",
      location = "Chennai, Tamil Nadu",
      nirfRank = 1,
      established = 1959,
      avgPackageLpa = 22.8,
      highestPackageLpa = "₹1.98 Cr (International)",
      cseCutoff = 145,
      eceCutoff = 720,
      mechCutoff = 2450,
      overview = "Ranked #1 in NIRF Overall Engineering for 8 consecutive years. Known for IITM Research Park, deepest quantum & semiconductor labs, and serene forested deer campus.",
      keyBranches = listOf("Computer Science", "Electrical (EE)", "Aerospace", "Naval Architecture", "Data Science"),
      campusHighlights = listOf("IITM Research Park", "Shaastra & Saarang festivals", "Nature reserve campus with wildlife", "Highest patent output")
    ),
    College(
      id = "iitk",
      name = "Indian Institute of Technology Kanpur",
      shortName = "IIT Kanpur",
      location = "Kanpur, Uttar Pradesh",
      nirfRank = 4,
      established = 1959,
      avgPackageLpa = 22.1,
      highestPackageLpa = "₹1.90 Cr (Domestic/Intl)",
      cseCutoff = 215,
      eceCutoff = 1250,
      mechCutoff = 2900,
      overview = "Pioneered computer science education in India with its own private airstrip, supersonic wind tunnels, and unmatched academic freedom.",
      keyBranches = listOf("Computer Science", "Electrical", "Mechanical", "Aerospace Engineering", "Materials"),
      campusHighlights = listOf("Private Flight Lab with Airstrip", "Antaragni & Techkriti festivals", "Open 24/7 PK Kelkar Library", "Freedom in course credits")
    ),
    College(
      id = "iitkgp",
      name = "Indian Institute of Technology Kharagpur",
      shortName = "IIT Kharagpur",
      location = "Kharagpur, West Bengal",
      nirfRank = 5,
      established = 1951,
      avgPackageLpa = 20.8,
      highestPackageLpa = "₹2.68 Cr (International)",
      cseCutoff = 260,
      eceCutoff = 1400,
      mechCutoff = 3400,
      overview = "The first and largest IIT campus covering 2,100 acres. Offers the most diverse range of interdisciplinary engineering and law programs.",
      keyBranches = listOf("Computer Science", "Electronics & ECE", "Ocean Engineering", "Industrial", "Mining"),
      campusHighlights = listOf("2,100 acre massive township", "Spring Fest & Kshitij", "Oldest IIT legacy", "Over 20+ residential halls")
    ),
    College(
      id = "bits",
      name = "Birla Institute of Technology and Science, Pilani",
      shortName = "BITS Pilani",
      location = "Pilani, Rajasthan",
      nirfRank = 18,
      established = 1964,
      avgPackageLpa = 20.5,
      highestPackageLpa = "₹1.33 Cr",
      cseCutoff = 331, // BITSAT score
      eceCutoff = 295,
      mechCutoff = 244,
      overview = "Top private institute on par with top 5 IITs. Legendary zero-attendance policy, Practice School (6 months industry internship), and elite founder ecosystem.",
      keyBranches = listOf("Computer Science", "ECE", "EEE", "Mechanical", "M.Sc. Economics Dual"),
      campusHighlights = listOf("Zero mandatory attendance rule", "Practice School PS-1 and PS-2", "Oasis cultural fest", "Vibrant alumni network")
    )
  )

  val mentors: List<Mentor> = listOf(
    Mentor(
      id = "m1",
      name = "Anjali Sharma",
      airRank = 42,
      college = "IIT Bombay",
      branch = "Computer Science & Engg",
      tagLine = "AIR 42 • Maths 112/120 • 99.98 %ile",
      bio = "Mentored 400+ JEE aspirants to top 1000 ranks. Believer in minimal books, maximum PYQ revisions and mistake-analysis notebooks.",
      physicsScore = 95,
      chemistryScore = 98,
      mathScore = 112,
      topAdvice = "Don't jump between 10 coaching modules. Pick one standard material, solve PYQs (2019-2025) at least 3 times, and maintain a dedicated 'Blunder Notebook'.",
      timetable = "6:00 AM - 9:00 AM (Physics problem solving) | 10:30 AM - 1:30 PM (Maths calculus & algebra) | 3:00 PM - 6:00 PM (Chemistry inorganic NCERT + Organic) | 8:00 PM - 10:30 PM (Mock test / Error review)",
      rating = 4.96,
      sessionCount = 380,
      isAvailable = true,
      avatarSeed = "anjali"
    ),
    Mentor(
      id = "m2",
      name = "Vikram Singh",
      airRank = 118,
      college = "IIT Delhi",
      branch = "Electrical Engineering",
      tagLine = "AIR 118 • Physics 115/120 • KVPY SX Fellow",
      bio = "Physics enthusiast and JEE Advanced ranker. Cracked JEE with self-discipline and strong conceptual visualization.",
      physicsScore = 115,
      chemistryScore = 90,
      mathScore = 96,
      topAdvice = "In Physics, derivation of concepts is 10x more important than memorizing formulas. If you can visualize the free-body diagram or electric field lines, questions solve themselves in 90 seconds.",
      timetable = "7:00 AM - 10:00 AM (Deep problem session) | 11:30 AM - 2:30 PM (Physics rotation/mechanics) | 4:00 PM - 7:00 PM (Mock analysis) | 9:00 PM - 11:00 PM (Light formula revision)",
      rating = 4.92,
      sessionCount = 290,
      isAvailable = true,
      avatarSeed = "vikram"
    ),
    Mentor(
      id = "m3",
      name = "Ishita Kapoor",
      airRank = 235,
      college = "IIT Madras",
      branch = "Mechanical Engineering",
      tagLine = "AIR 235 • Chemistry 116/120 • NCERT Specialist",
      bio = "Scored 116/120 in JEE Advanced Chemistry. Expert in Inorganic line-by-line mastery and Organic reaction flowcharts.",
      physicsScore = 88,
      chemistryScore = 116,
      mathScore = 90,
      topAdvice = "Chemistry is the easiest rank-booster in JEE. Treat NCERT as the bible for Inorganic and Biomolecules. Make A3 reaction roadmaps for Organic and revise every morning for 20 mins.",
      timetable = "5:30 AM - 7:30 AM (Inorganic NCERT read) | 9:00 AM - 1:00 PM (Organic & Physical numericals) | 2:30 PM - 6:00 PM (Maths integration) | 7:30 PM - 10:30 PM (Physics full test)",
      rating = 4.95,
      sessionCount = 340,
      isAvailable = false,
      avatarSeed = "ishita"
    ),
    Mentor(
      id = "m4",
      name = "Arjun Mehta",
      airRank = 312,
      college = "IIT Kanpur",
      branch = "Aerospace Engineering",
      tagLine = "AIR 312 • JEE Main 99.85 %ile • Backlog Recovery Guide",
      bio = "Cleared an 8-month Class 11 backlog in 4 months and scored AIR 312. Specialized in strategic backlog clearing & exam temperament.",
      physicsScore = 92,
      chemistryScore = 96,
      mathScore = 104,
      topAdvice = "Having backlogs is normal. Never stop current 12th syllabus to clear 11th. Dedicate 2 hours daily in the evening specifically for high-weightage backlog topics only.",
      timetable = "6:30 AM - 9:30 AM (Current 12th topic) | 11:00 AM - 2:00 PM (Maths practice) | 3:30 PM - 5:30 PM (Dedicated Backlog slot) | 7:00 PM - 10:00 PM (Full Mock Test Drill)",
      rating = 4.89,
      sessionCount = 210,
      isAvailable = true,
      avatarSeed = "arjun"
    )
  )

  val sampleChatMessages: Map<String, List<ChatMessage>> = mapOf(
    "m1" to listOf(
      ChatMessage("c1", "m1", "Hey Aarav! How is your JEE preparation going this week? What are you focusing on currently?", false, "10:30 AM"),
      ChatMessage("c2", "m1", "Hi Anjali di! I'm struggling with time management in Maths calculus and feel slow in definite integrals.", true, "10:32 AM"),
      ChatMessage("c3", "m1", "That's very common! In definite integration, 80% of JEE questions use King's property (∫f(a+b-x)dx) or Leibnitz rule. Instead of solving general integrals, master these two properties thoroughly with 30 PYQs. You'll instantly see your speed double!", false, "10:34 AM"),
      ChatMessage("c4", "m1", "Thanks di! How many questions should I target daily per subject?", true, "10:36 AM"),
      ChatMessage("c5", "m1", "Aim for quality over quantity: 20-25 challenging quality questions per subject per day under a timer. Always analyze every unattempted or wrong question before sleeping!", false, "10:38 AM")
    ),
    "m2" to listOf(
      ChatMessage("c10", "m2", "Hello! I am Vikram. Ready to tackle your Physics doubts or review your mock test strategy today?", false, "09:15 AM"),
      ChatMessage("c11", "m2", "Hi Vikram bhaiya! Rotational motion questions in mocks take me 5+ minutes each.", true, "09:18 AM"),
      ChatMessage("c12", "m2", "Let's fix that! For Rotation, break problems into 3 sequential steps: 1) Torque equation about Instantaneous Center of Rotation (ICOR), 2) Work-Energy conservation, 3) Angular momentum conservation. Never jump directly into equations without fixing the reference axis.", false, "09:22 AM")
    )
  )

  val motivationalQuotes: List<MotivationalQuote> = listOf(
    MotivationalQuote(
      id = "q1",
      quote = "Heroes are made by the path they choose, not the powers they are graced with. Build your own arc reactor in your study room: discipline, focus, and relentless problem solving.",
      authorOrHero = "Tony Stark / Iron Man",
      heroRole = "Genius Innovator & Relentless Problem Solver",
      category = QuoteCategory.AVENGERS,
      subtext = "Every hard numerical you crack powers your personal arc reactor towards AIR under 500.",
      isBookmarked = true,
      heroType = "IRON_MAN",
      accentColorHex = 0xFFFFD700
    ),
    MotivationalQuote(
      id = "q2",
      quote = "I can do this all day. When 3-hour mock tests drain your energy, dig in your heels. That extra hour of focused revision is where Top 100 ranks are forged.",
      authorOrHero = "Steve Rogers / Captain America",
      heroRole = "Embodiment of Unyielding Grit & Discipline",
      category = QuoteCategory.AVENGERS,
      subtext = "Consistency beats natural talent every single time. Stand your ground.",
      isBookmarked = true,
      heroType = "CAPTAIN_AMERICA",
      accentColorHex = 0xFF42A5F5
    ),
    MotivationalQuote(
      id = "q3",
      quote = "Whatever it takes. The price of an IIT Bombay / Delhi seat isn't luck—it's 100% daily dedication, honest error analysis, and zero excuses.",
      authorOrHero = "The Avengers",
      heroRole = "Endgame Protocol",
      category = QuoteCategory.AVENGERS,
      subtext = "No backlogs are invincible when you work with unwavering commitment.",
      isBookmarked = false,
      heroType = "AVENGERS",
      accentColorHex = 0xFFFF5252
    ),
    MotivationalQuote(
      id = "q4",
      quote = "I went forward in time... to view alternate futures. To see all the possible outcomes of the coming exam. In the 1 outcome where you enter your dream IIT, you gave your absolute everything today.",
      authorOrHero = "Doctor Strange",
      heroRole = "Master of Time & All 14,000,605 Possibilities",
      category = QuoteCategory.AVENGERS,
      subtext = "Your future rank is being calculated right now by what you study in the next 60 minutes.",
      isBookmarked = false,
      heroType = "DOCTOR_STRANGE",
      accentColorHex = 0xFF00E676
    ),
    MotivationalQuote(
      id = "q5",
      quote = "I choose to run towards my problems, and not away from them. Because that's what heroes do. Attack your weakest chapters first!",
      authorOrHero = "Thor Odinson",
      heroRole = "God of Thunder & Raw Determination",
      category = QuoteCategory.AVENGERS,
      subtext = "Face rotational motion, ionic equilibrium, and complex numbers with thunderous courage.",
      isBookmarked = false,
      heroType = "THOR",
      accentColorHex = 0xFF00E5FF
    ),
    MotivationalQuote(
      id = "q6",
      quote = "With great dream comes great responsibility. Every practice question solved is a sacred promise kept to your future self.",
      authorOrHero = "Peter Parker / Spider-Man",
      heroRole = "The Friendly Neighborhood Aspirant",
      category = QuoteCategory.AVENGERS,
      subtext = "When physics gets heavy, remember who you're doing this for. Keep swinging forward.",
      isBookmarked = true,
      heroType = "SPIDER_MAN",
      accentColorHex = 0xFFFF1744
    ),
    MotivationalQuote(
      id = "q7",
      quote = "Exam pressure says: 'I am inevitable.' The true JEE Aspirant smiles and replies: 'And I am persistent.'",
      authorOrHero = "Aspirant Iron Will",
      heroRole = "The Unstoppable Mindset",
      category = QuoteCategory.EXAM_CONFIDENCE,
      subtext = "Fear is just a physiological sensation. Convert it into razor-sharp focus.",
      isBookmarked = false,
      heroType = "AVENGERS",
      accentColorHex = 0xFFAB47BC
    ),
    MotivationalQuote(
      id = "q8",
      quote = "Dream is not that which you see while sleeping; it is something that does not let you sleep. To succeed in your mission, you must have single-minded devotion to your goal.",
      authorOrHero = "Dr. A.P.J. Abdul Kalam",
      heroRole = "Missile Man & Former President of India",
      category = QuoteCategory.LEGENDS_OF_SCIENCE,
      subtext = "Small aim is a crime; have great aim. Work with relentless purity of purpose.",
      isBookmarked = true,
      heroType = "SCIENTIST",
      accentColorHex = 0xFFFFB300
    ),
    MotivationalQuote(
      id = "q9",
      quote = "Study hard what interests you the most in the most undisciplined, irreverent, and original manner possible. Fall in love with understanding how reality works.",
      authorOrHero = "Richard Feynman",
      heroRole = "Nobel Laureate in Quantum Electrodynamics",
      category = QuoteCategory.LEGENDS_OF_SCIENCE,
      subtext = "Physics isn't about memorizing tricks; it's about seeing the deep elegance of nature.",
      isBookmarked = false,
      heroType = "SCIENTIST",
      accentColorHex = 0xFF26C6DA
    ),
    MotivationalQuote(
      id = "q10",
      quote = "The present is theirs; the future, for which I really worked, is mine. Do not care about who is doing what today. Let your silent labor build an empire.",
      authorOrHero = "Nikola Tesla",
      heroRole = "Visionary Electrical Pioneer",
      category = QuoteCategory.LEGENDS_OF_SCIENCE,
      subtext = "Sacrifice momentary entertainment for permanent engineering legacy.",
      isBookmarked = false,
      heroType = "SCIENTIST",
      accentColorHex = 0xFF7C4DFF
    ),
    MotivationalQuote(
      id = "q11",
      quote = "You have a right to perform your prescribed duties, but you are not entitled to the fruits of your actions. Never consider yourself the cause of results, nor be attached to inaction.",
      authorOrHero = "Bhagavad Gita (Karma Yoga 2.47)",
      heroRole = "Timeless Wisdom on Effort & Detachment",
      category = QuoteCategory.GRIT_AND_DISCIPLINE,
      subtext = "Release mock test anxiety. Put 100% honesty into today's preparation block.",
      isBookmarked = false,
      heroType = "PHILOSOPHY",
      accentColorHex = 0xFFFF7043
    ),
    MotivationalQuote(
      id = "q12",
      quote = "It's not about how much we lost. It's about how much we have left. Backlogs do not define your final score; your comeback strategy does.",
      authorOrHero = "Tony Stark",
      heroRole = "The Comeback Protocol",
      category = QuoteCategory.GRIT_AND_DISCIPLINE,
      subtext = "Even starting today with disciplined 8 hours daily can turn your rank around completely.",
      isBookmarked = false,
      heroType = "IRON_MAN",
      accentColorHex = 0xFFFFD700
    )
  )

  val targetPresets: List<TargetGoalPreset> = listOf(
    TargetGoalPreset(
      id = "preset_iitb_cse",
      title = "Top 60 AIR • IIT Bombay CSE",
      collegeTarget = "IIT Bombay (Computer Science)",
      targetAir = 60,
      targetScoreMain = 285,
      targetScoreAdv = 295,
      iconEmoji = "🏆"
    ),
    TargetGoalPreset(
      id = "preset_iitd_mnc",
      title = "Top 350 AIR • IIT Delhi MnC / CSE",
      collegeTarget = "IIT Delhi (Mathematics & Computing)",
      targetAir = 350,
      targetScoreMain = 270,
      targetScoreAdv = 265,
      iconEmoji = "⚡️"
    ),
    TargetGoalPreset(
      id = "preset_top_iits",
      title = "Top 1,500 AIR • Top 5 IITs Core / Electrical",
      collegeTarget = "IIT Bombay / Delhi / Madras (EE/Mech)",
      targetAir = 1500,
      targetScoreMain = 245,
      targetScoreAdv = 220,
      iconEmoji = "🏛️"
    ),
    TargetGoalPreset(
      id = "preset_nitt_cse",
      title = "Top 4,000 AIR • NIT Trichy / Warangal CSE",
      collegeTarget = "NIT Trichy (CSE) / IIIT Hyderabad ECE",
      targetAir = 4000,
      targetScoreMain = 225,
      targetScoreAdv = 185,
      iconEmoji = "💻"
    ),
    TargetGoalPreset(
      id = "preset_top_nits",
      title = "Top 10,000 AIR • Premier NITs & Top IIITs",
      collegeTarget = "NIT Surathkal / Rourkela / Calicut",
      targetAir = 10000,
      targetScoreMain = 195,
      targetScoreAdv = 150,
      iconEmoji = "🎯"
    ),
    TargetGoalPreset(
      id = "preset_qualify_adv",
      title = "Qualify for Advanced • Top 2.5 Lakh Cutoff",
      collegeTarget = "JEE Advanced Qualified (~93.5%ile)",
      targetAir = 95000,
      targetScoreMain = 135,
      targetScoreAdv = 110,
      iconEmoji = "🚀"
    )
  )

  val avatarPresets: List<AvatarPreset> = listOf(
    AvatarPreset(
      id = "iron_man",
      label = "Iron Man Arc Core",
      subtitle = "Mark 85 Nano-Reactor",
      drawableRes = com.example.R.drawable.ic_arc_reactor,
      emoji = "⚡️",
      themeColorHex = 0xFF00E5FF
    ),
    AvatarPreset(
      id = "cap",
      label = "Captain America",
      subtitle = "Vibranium Star Shield",
      drawableRes = com.example.R.drawable.ic_hero_shield,
      emoji = "🛡️",
      themeColorHex = 0xFF42A5F5
    ),
    AvatarPreset(
      id = "thor",
      label = "Thor Odinson",
      subtitle = "Mjolnir Thunder Surge",
      drawableRes = com.example.R.drawable.ic_thor_hammer,
      emoji = "⚡️",
      themeColorHex = 0xFF00E5FF
    ),
    AvatarPreset(
      id = "strange",
      label = "Doctor Strange",
      subtitle = "Mystic Mandala Runes",
      drawableRes = com.example.R.drawable.ic_doctor_strange,
      emoji = "🔮",
      themeColorHex = 0xFFFFB300
    ),
    AvatarPreset(
      id = "spiderman",
      label = "Spider-Man",
      subtitle = "Integrated Web Suit",
      drawableRes = com.example.R.drawable.ic_spiderman,
      emoji = "🕷️",
      themeColorHex = 0xFFFF1744
    ),
    AvatarPreset(
      id = "infinity_gauntlet",
      label = "Infinity Core",
      subtitle = "All 6 Cosmic Stones",
      drawableRes = com.example.R.drawable.ic_infinity_gauntlet,
      emoji = "💎",
      themeColorHex = 0xFFAB47BC
    ),
    AvatarPreset(
      id = "avengers_assemble",
      label = "Avengers Assemble",
      subtitle = "Earth's Mightiest Logo",
      drawableRes = com.example.R.drawable.ic_avengers_logo,
      emoji = "⭐",
      themeColorHex = 0xFFFFD700
    ),
    AvatarPreset(
      id = "aspirant_boy",
      label = "JEE Warrior (Sonu)",
      subtitle = "Top Rank Aiming Scholar",
      drawableRes = null,
      emoji = "👨‍🎓",
      themeColorHex = 0xFF4CAF50
    ),
    AvatarPreset(
      id = "aspirant_girl",
      label = "IIT Aspirant Star",
      subtitle = "AIR 1 Mission",
      drawableRes = null,
      emoji = "👩‍🎓",
      themeColorHex = 0xFFFF4081
    ),
    AvatarPreset(
      id = "initials",
      label = "Custom Initials (SK)",
      subtitle = "Minimal Dynamic Monogram",
      drawableRes = null,
      emoji = "✨",
      themeColorHex = 0xFF7C4DFF
    )
  )

  val whatIfScenarios: List<WhatIfScenario> = listOf(
    WhatIfScenario(
      id = "sc_negative_fix",
      title = "Zero Negative Marking Shield",
      description = "Stop blind guessing: Convert 5 negative marks into safe skips to gain +25 net marks without reading new chapters.",
      deltaPhysics = 8,
      deltaChemistry = 8,
      deltaMath = 9,
      iconEmoji = "🛡️",
      estimatedHoursNeeded = 10
    ),
    WhatIfScenario(
      id = "sc_chem_high_yield",
      title = "Chemistry High-Yield Rocket",
      description = "Master Modern NCERT Inorganic + Coordination Compounds + GOC/Hydrocarbons for immediate high-accuracy questions.",
      deltaPhysics = 4,
      deltaChemistry = 18,
      deltaMath = 4,
      iconEmoji = "🧪",
      estimatedHoursNeeded = 25
    ),
    WhatIfScenario(
      id = "sc_math_easy_kill",
      title = "Math Low-Hanging Fruit Trio",
      description = "Conquer Matrices & Determinants, Vector 3D, and Statistics to guarantee 4-5 direct questions in Math paper.",
      deltaPhysics = 4,
      deltaChemistry = 4,
      deltaMath = 18,
      iconEmoji = "📐",
      estimatedHoursNeeded = 30
    ),
    WhatIfScenario(
      id = "sc_physics_formula_speed",
      title = "Physics Formula Sprint",
      description = "Master Modern Physics (Photoelectric, Atoms, Nuclei, Semiconductors) + Current Electricity for rapid 30-second solves.",
      deltaPhysics = 16,
      deltaChemistry = 6,
      deltaMath = 4,
      iconEmoji = "⚡️",
      estimatedHoursNeeded = 20
    ),
    WhatIfScenario(
      id = "sc_all_round_leap",
      title = "30-Day Total Rank Acceleration",
      description = "Rigorous 100-hour timed PYQ solving across all 3 subjects to unlock +36 total marks and massive 15,000+ AIR leap!",
      deltaPhysics = 12,
      deltaChemistry = 14,
      deltaMath = 10,
      iconEmoji = "🚀",
      estimatedHoursNeeded = 85
    )
  )

  val syllabusProgressHistory: List<SyllabusProgressPoint> = listOf(
    SyllabusProgressPoint("W1 Apr", 24f, 20f, 18f, 20.7f, 15f, 7, 180),
    SyllabusProgressPoint("W3 Apr", 32f, 28f, 24f, 28.0f, 22f, 10, 310),
    SyllabusProgressPoint("W1 May", 40f, 35f, 30f, 35.0f, 30f, 13, 490),
    SyllabusProgressPoint("W3 May", 48f, 44f, 38f, 43.3f, 38f, 16, 680),
    SyllabusProgressPoint("W1 Jun", 56f, 52f, 45f, 51.0f, 46f, 19, 890),
    SyllabusProgressPoint("W3 Jun", 63f, 60f, 52f, 58.3f, 54f, 22, 1120),
    SyllabusProgressPoint("W1 Jul", 70f, 68f, 58f, 65.3f, 62f, 25, 1340),
    SyllabusProgressPoint("W3 Jul", 76f, 74f, 65f, 71.7f, 70f, 28, 1590),
    SyllabusProgressPoint("W1 Aug", 82f, 80f, 71f, 77.7f, 76f, 30, 1820),
    SyllabusProgressPoint("Current (W4 Aug)", 88f, 85f, 78f, 83.7f, 82f, 33, 2060)
  )

  val practiceTestScoreHistory: List<PracticeTestScorePoint> = listOf(
    PracticeTestScorePoint("pt1", "Diagnostic Mock #01", "04 Apr", "JEE Main", 300, 142, 48, 52, 42, 91.20, 96500, 71.5),
    PracticeTestScorePoint("pt2", "Part Test Phy & Chem #02", "18 Apr", "JEE Main", 300, 158, 56, 60, 42, 93.65, 68000, 74.0),
    PracticeTestScorePoint("pt3", "Full Length Mock #03", "02 May", "JEE Main", 300, 166, 58, 64, 44, 94.80, 56000, 76.2),
    PracticeTestScorePoint("pt4", "JEE Main Speed Drill #04", "16 May", "JEE Main", 300, 178, 64, 66, 48, 96.10, 42000, 79.5),
    PracticeTestScorePoint("pt5", "All India Test Series #05", "30 May", "JEE Main", 300, 186, 68, 68, 50, 96.90, 33500, 81.0),
    PracticeTestScorePoint("pt6", "Mid-Year Revision Mock #06", "13 Jun", "JEE Main", 300, 198, 72, 72, 54, 97.80, 23800, 83.5),
    PracticeTestScorePoint("pt7", "JEE Advanced Prep Paper #01", "27 Jun", "JEE Advanced", 360, 182, 65, 62, 55, 96.40, 6800, 75.0),
    PracticeTestScorePoint("pt8", "Full Syllabus Mock #08", "11 Jul", "JEE Main", 300, 212, 76, 78, 58, 98.45, 16200, 85.8),
    PracticeTestScorePoint("pt9", "All India Open Mock #09", "25 Jul", "JEE Main", 300, 224, 80, 82, 62, 98.92, 11400, 87.2),
    PracticeTestScorePoint("pt10", "Rank Booster Mock #10", "08 Aug", "JEE Main", 300, 232, 82, 84, 66, 99.15, 8900, 89.0),
    PracticeTestScorePoint("pt11", "JEE Advanced Paper 1 & 2 #02", "15 Aug", "JEE Advanced", 360, 218, 76, 78, 64, 98.90, 2400, 81.5),
    PracticeTestScorePoint("pt12", "All India JEE Main Mock #14", "24 Aug", "JEE Main", 300, 242, 86, 86, 70, 99.35, 6840, 91.5)
  )
}

