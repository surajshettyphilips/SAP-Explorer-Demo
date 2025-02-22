provider "aws" {
  region = var.aws_region
}

resource "aws_ecr_repository" "spring_reactive" {
  name                 = "spring-reactive"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

output "ecr_repository_url" {
  value = aws_ecr_repository.spring_reactive.repository_url
}
