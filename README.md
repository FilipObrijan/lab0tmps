# Simple CLI Todo — a tiny project that teaches 3 solid principles
## Project summary

Build a tiny command-line Todo application that can add, list, complete, and remove tasks. Keep it deliberately small so you can focus on structure and design.

Why this project? It's minimal but practical, and it's a perfect playground to apply three important design principles and keep code clean and testable.

## The 3 solid principles we will practice

- Single Responsibility Principle (SRP): Each module or class should have one reason to change. Separate concerns: command parsing, persistence, and business logic.
- Dependency Inversion Principle (DIP): High-level modules (the app logic) should depend on abstractions (interfaces) not concrete implementations (file, memory). This makes testing and swapping storage simple.
- Open/Closed Principle (OCP): Make the core behavior open for extension (you can add a new command or storage provider) but closed for modification (don't change core app logic to add new storage).

These three principles keep the project tiny and maintainable while teaching practical design choices.

## Contract (very small)

- Inputs: simple commands via CLI arguments (e.g. `add "Buy milk"`, `list`, `done 2`, `remove 2`).
- Outputs: console text; exit code 0 for success, non-zero for errors.
- Data shape: a task is { id: int, text: string, completed: bool, created_at: ISO-8601 }
- Errors: invalid commands, missing IDs, and storage failures should return clean messages and non-zero exit codes.

Success = user can run `add`, `list`, `done`, and `remove` and see expected results. Tests cover these operations using an in-memory storage implementation.
# Grocery List CLI — report showing 3 SOLID principles

This file is a concise report for a very small project idea (Grocery List CLI) and shows how three SOLID principles are applied in practice.

Project idea
- A tiny command-line Grocery List app where you can add, view, check off, and delete grocery items.

Why this idea?
- Extremely small surface area (4 commands), easy to implement and test, and perfect for demonstrating design principles without heavy boilerplate.

Scope (minimal):
- Commands: `add "item"`, `list`, `check <id>`, `remove <id>`
- Data shape: { id: int, name: string, checked: bool, created_at: ISO-8601 }

The three principles implemented below: Single Responsibility Principle (SRP), Dependency Inversion Principle (DIP), Open/Closed Principle (OCP).

## Contract
- Inputs: CLI args (strings and integers).
- Outputs: human-readable console text and exit codes (0 success, >0 errors).
- Storage: pluggable (in-memory for tests, file-based for simple persistence).

Success criteria
- Basic commands work and are testable with an in-memory storage implementation.

## Suggested minimal layout (language-agnostic)
- `cli` (entry) — parse args, dispatch commands
- `app` (core) — business logic, depends on `Storage` abstraction
- `storage` — defines `Storage` interface + concrete implementations (`MemoryStorage`, `FileStorage`)
- `models` — `Item` data type
- `tests` — unit tests using `MemoryStorage`

Example file names (Python):
- `grocery/__main__.py`  # CLI entry
- `grocery/app.py`       # App class depends on Storage
- `grocery/storage.py`   # Storage interface + implementations
- `grocery/models.py`    # Item dataclass
- `tests/test_app.py`    # unit tests

## How each principle is applied

1) Single Responsibility Principle (SRP)
- Each module has one responsibility:
	- `__main__` (CLI) only parses arguments and delegates.
	- `app` contains only business rules (add, list, check, remove).
	- `storage` handles persistence (load/save) and nothing else.
	- `models` only defines the `Item` structure.

Benefit: changing storage format or CLI parsing doesn't affect business logic.

2) Dependency Inversion Principle (DIP)
- The `App` depends on a `Storage` abstraction (interface/protocol), not a concrete storage.
- Example (pseudo-signature):

	class Storage:
			def load_items() -> List[Item]
			def save_items(items: List[Item]) -> None

	class App:
			def __init__(self, storage: Storage):
					self.storage = storage

- Tests instantiate `App` with `MemoryStorage`. Production can pass `FileStorage`.

Benefit: swapping storage for tests or new persistence requires no change to `App`.

3) Open/Closed Principle (OCP)
- Design the app so new features are added by extension, not by modifying existing core logic.
- Examples of extensibility:
	- Add a new storage provider (`SqliteStorage`) by implementing `Storage`.
	- Add a new command (e.g., `due <date>`) by creating a new command handler that calls `App` methods.

How to keep core code closed for modification:
- Keep command registration separate from `App` internals. `App` exposes small, stable methods (add, list, check, remove).
- Command parser or a command registry composes new handlers.

## Minimal pseudo implementation sketch (to keep ideas concrete)

- models.py

	Item = { id: int, name: str, checked: bool, created_at: str }

- storage.py (interface)

	class Storage:
			def load_items() -> List[Item]
			def save_items(items: List[Item]) -> None

- app.py

	class App:
			def __init__(self, storage: Storage):
					self.storage = storage

			def add(name: str) -> Item: ...
			def list() -> List[Item]: ...
			def check(id: int) -> Item: ...
			def remove(id: int) -> None: ...

Tests use a `MemoryStorage` with a simple list; production can use `FileStorage` that reads/writes JSON.

## Example usage (PowerShell)

```powershell
# add an item
python -m grocery add "Bananas"

# list items
python -m grocery list

# check item 1
python -m grocery check 1

# remove item 1
python -m grocery remove 1
```

## Edge cases and notes
- Prevent adding empty names (validate in `App`).
- Return clear errors for unknown IDs.
- For file storage, consider simple file locking or accept last-write-wins for this tiny project.

## Small checklist for implementation
- [ ] Create `Item` model
- [ ] Create `Storage` interface and `MemoryStorage`
- [ ] Implement `App` using `Storage` (SRP + DIP)
- [ ] Build CLI entry that uses `App` (keeps `App` unchanged when extending commands)
- [ ] Add tests that use `MemoryStorage`

If you want, I can now scaffold this project in Python (files + tests + basic implementation) or provide a single-file implementation that still follows these principles in a lightweight way. Tell me which you prefer.
- A `DiscountCalculator` interface is created.

