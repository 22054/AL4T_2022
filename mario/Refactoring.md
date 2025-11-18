# Listing issues

## SOLID principle violations

### Violation of SRP in GameEngine

Does too many things. Is a god object.

### Violation of SRP in GameObject

Handling both physics and rendering.

### Violation of DIP of ImageLoader

MarioForm and SuperMushroom are creating instances of ImageLoader.

### Violation of DIP of GameEngine

Hard GameEngine dependency in Mario class.

### Violation of OCP in MapCreator

### Violation of OCP in Map.drawMap()

It seems like we could iterate on a list of IRenderable instead.

Big "if-else".

## Bugs

### Game music stops at some point

### Collision bugs

### Concurrency issues

### Sounds overlap

## Dependency Injection

### Dagger 2 integration (mario module)

We integrated dependency injection using Dagger (core, platform-agnostic) in the `mario` module to formalize and centralize wiring:

- Component: `di.MarioComponent` (singleton) is the root component for the game.
- Module: `di.MarioModule` binds interfaces to implementations:
  - `CameraInterface -> Camera`
  - `ISoundManager -> SoundManager`
  - `IImageLoader -> ImageLoader`
  - `IMapCreator -> MapCreator`
  - `IMapManager -> MapManager`
  - `IMarioEngineFacade -> GameEngine`
  - `IGameEngine -> GameEngine`

Constructors annotated with `@Inject`:

- `manager.Camera`
- `view.ImageLoader`
- `manager.SoundManager`
- `manager.MapCreator` (also made `public`)
- `manager.MapManager`
- `manager.GameEngine`

Bootstrap:

- `GameEngine.main(...)` now builds the component via `DaggerMarioComponent.create()` and obtains a `GameEngine` from DI. The `GameEngine` constructor preserves existing behavior (creates `UIManager` and starts the loop).

Build configuration:

- Added Dagger dependencies and annotation processing in `mario/build.gradle.kts`:
  - `implementation("com.google.dagger:dagger:2.51.1")`
  - `annotationProcessor("com.google.dagger:dagger-compiler:2.51.1")`

Testing notes:

- Existing tests continue to work unchanged; optional future improvement is a dedicated TestComponent to bind fakes like `FakeImageLoader` and `FakeMapCreator` if you want to perform DI-based integration tests.